import threading
import socket
import select
import random
import math
import time
import json
import uuid
from ship import Ship
from color import Color


class Game:

	def __init__(self):

		self.players = []
		self.bots = []
		self.playerConns = {}
		self.playerNames = []
		self.frame = 0
		self.scoreString = ''

		self.width = 640*1
		self.height = 640*1
		self.scale = 1
		self.framerate = 100
		
		self.serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		#self.serverSocket.bind(('0.0.0.0', 7007))
		self.serverSocket.bind(('', 7007))
		self.serverSocket.listen(5)

		self.serverList = [self.serverSocket]
		self.connList = {}


	def initGraphic(self):

		self.root = Tk()
		self.root.title('BotWars')
		self.canvas = Canvas(self.root, width = self.width/self.scale, height = self.height/self.scale, bg = 'black')
		self.canvas.pack()


	def updateFrame(self):

		self.canvas.delete("all")
		self.scoreBoard = self.canvas.create_text(5, 5, text = self.scoreString, anchor = 'nw', fill = 'white')
		self.frameBoard = self.canvas.create_text(5, self.height/self.scale, text = str(self.frame), anchor = 'sw', fill = 'white')

		for player in self.players:
			if player.active:
				player.drawMaster()

		self.canvas.after(1, self.updateFrame)


	def addPlayer(self, conn, name, color):
		try:
			playerUUID = str(uuid.uuid4())
			check = Color(color)
			print()
			if not check.valid:
				color = '#6a7e25'
			self.playerConns[playerUUID] = Ship(self, name, color, playerUUID) 
			self.playerNames.append(name)
			self.connList[conn] = playerUUID
			self.players.append(self.playerConns[playerUUID])
			print(name, 'joined the game. Their color is', color + '.')
			return {'result': playerUUID}
		except:
			return {'status': False}


	def newFrame(self):

		self.scoreList = sorted(self.players, key = self.getPlayerScore, reverse = True)
		self.nameList = [str(player.playerID) + ': ' + str(player.score) for player in self.scoreList if player.active]
		self.scoreString = '\n'.join(self.nameList[:10])


		self.getMessages()

		for player in self.players:
			player.moveShip()
			if self.frame - player.lastHitFrame > 50:
				player.health = min(player.health+1, 200)
			player.energy = min(player.energy+1, 100)

		for bot in self.bots:
			if self.frame % (50*self.width//640) == 0:
				bot.setAngle(360*random.random())
			if self.frame % 50 == 0:
				bot.fireBullet()
				

		for player in self.players:
			if player.alive == False and self.frame > player.deathFrame+100 and player.active:
				player.respawnShip()

		#if sum(1 for player in self.players if player.alive) == 1:
		#	self.winGame()
		#else:
		self.frame += 1
		time.sleep(1/self.framerate)


	def winGame(self):
		winner = [player.playerID if player.alive else '' for player in self.players]
		winner.sort()
		scoreList = sorted(self.players, key = self.getPlayerScore, reverse = True)
		print('\nGame Won By: ', winner[-1])
		print()
		for player in scoreList:
			print(player.playerID+': ', player.score)
		self.root.destroy()

	def getPlayerScore(self, player):
		return player.score


	def getMessages(self):
		(rtr, rtw, ie) = select.select(self.serverList, [], [], 0)
		for element in rtr:
			if element == self.serverSocket:
				(conn, addr) = self.serverSocket.accept()
				self.serverList.append(conn)
			else:
				try:
					data = element.recv(1024)
					if not data and element in self.serverList:
						self.serverList.remove(element)
						self.playerConns[self.connList[element]].active = False
						self.playerNames.remove(self.playerConns[self.connList[element]].playerID)
						print(self.playerConns[self.connList[element]].playerID, 'disconnected.')
					else:
						obj = json.loads(data.decode().split('\0')[0])
						if obj:
							datasend = (json.dumps(self.processMessage(element, obj)) + '\0').encode()
							element.send(datasend)
				except:
					print('message processing error')
					if element in self.serverList:
						self.serverList.remove(element)


	def processMessage(self, conn, message):
		returnMessage = {}
		if 'name' in message and 'color' in message:
			if message['name'] not in self.playerNames:
				returnMessage = self.addPlayer(conn, message['name'], message['color'])
			else:
				returnMessage['status'] = False

		elif 'UUID' in message:
			if message['UUID'] in self.connList.values():
				self.connList = {key: value for key, value in self.connList.items() if value is not message['UUID']}
				self.connList[conn] = message['UUID']
				self.playerConns[self.connList[conn]].active = True
				self.playerNames.append(self.playerConns[self.connList[conn]].playerID)
				print(self.playerConns[self.connList[conn]].playerID, 'reconnected.')
			else:
				returnMessage['status'] = False


		elif conn in self.connList:

			if 'command' in message and 'value' in message:

				if message['command'] == 'angle':
					self.playerConns[self.connList[conn]].setAngle(message['value'])
				elif message['command'] == 'shield':
					if message['value'] == True:
						self.playerConns[self.connList[conn]].activateShield()
					else:
						self.playerConns[self.connList[conn]].disableShield()

			elif 'command' in message:

				if message['command'] == 'scanShips':
					scanned = self.playerConns[self.connList[conn]].scanShips()
					returnMessage['status'] = scanned['status']
					returnMessage['result'] = [{'x': ship[0], 'y': ship[1]} for ship in scanned['result']]
				elif message['command'] == 'boost':
					self.playerConns[self.connList[conn]].startBoost()
				elif message['command'] == 'fire':
					self.playerConns[self.connList[conn]].fireBullet()

				elif message['command'] == 'addBot':
					self.bots.append(Ship(self, 'BOT', '#a0a0a0', uuid.uuid4()))
					self.players.append(self.bots[-1])
				elif message['command'] == 'getStatus':
					returnMessage['result'] = {'health': self.playerConns[self.connList[conn]].health, 'energy': self.playerConns[self.connList[conn]].energy}
				elif message['command'] == 'getCoords':
					returnMessage['result'] = {'x': self.playerConns[self.connList[conn]].pos[0], 'y': self.playerConns[self.connList[conn]].pos[1]}
				elif message['command'] == 'getScore':
					returnMessage['result'] = self.playerConns[self.connList[conn]].score
				elif message['command'] == 'top10':
					unSorted = [(ship.playerID, ship.score) for ship in self.playerConns.values() if ship.active]
					sortedScore = sorted(unSorted, key = self.getSort, reverse = True)[:min(10, len(unSorted))]
					sortedTuples = [(str(i+1), sortedScore[i]) for i in range(len(sortedScore))]
					returnMessage['result'] = dict((key, value) for (key, value) in sortedTuples)

		else:
			returnMessage['status'] = False
		returnMessage['frame'] = self.frame
		returnMessage['position'] = self.getPlace(self.playerConns[self.connList[conn]])
		if 'status' not in returnMessage:
			returnMessage['status'] = True
		return returnMessage

	def getPlace(self, player):
		return sorted([player for player in self.players if player.active], key = self.getPlayerScore, reverse = True).index(player)+1


	def getSort(self, value):
		return value[1]


def serverStart(game):
	while True:
		game.newFrame()
		time.sleep(0)


def graphicStart(game):
	
	game.initGraphic()
	game.updateFrame()
	game.root.mainloop()

graphic = True
game = Game()
if graphic:
	from tkinter import *
	threading.Thread(target = graphicStart, args = [game]).start()
serverStart(game)