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
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    while True:
        s.sendto('Hello, world', ('165.123.178.171', 9000))
        logger.info('Sent packet')

        data = s.recv(1024)
        logger.info('Got back {}'.format(data))
        time.sleep(1)

