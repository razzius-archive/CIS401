import time
import socket
import logging
import sys
import signal

def signal_handler(*a):
    sys.exit(0)

signal.signal(signal.SIGINT, signal_handler)

logging.basicConfig(stream=sys.stdout,
                    level=logging.DEBUG,
                    format='%(asctime)s\t%(levelname)s\t%(message)s')

logger = logging.getLogger()

if __name__ == '__main__':
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect(('172.19.177.136', 9000))

    while True:
        s.sendall('Hello, world')
        s.recv(1024)
        logger.info('Sent packet')
        time.sleep(1)

