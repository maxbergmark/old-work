import time



class Row:
	



class Sudoku:

	def __init__(self, board):
		self.board = board


	def createStructure(self):
		self.rows = self.board.split('\n')
		self.matrix = [list(row) for row in self.rows]
		print(self.matrix)
		for i in range(9):
			print(self.matrix[i])
		self.columns = ['' for i in range(9)]
		for row in self.rows:
			for i in range(9):
				self.columns[i]


















board = '003020600\n900305001\n001806400\n008102900\n700000008\n006708200\n002609500\n800203009\n005010300'
game = Sudoku(board)
game.createStructure()