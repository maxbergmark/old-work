from queue import *

def worker():
    while True:
        item = q.get()
        do_work(item)
        q.task_done()

def main():

    q = Queue(maxsize=0)
    for i in range(num_worker_threads):
         t = Thread(target=worker)
         t.daemon = True
         t.start()

    for item in source():
        q.put(item)

    q.join()       # block until all tasks are done

main()