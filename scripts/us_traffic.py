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

delay = 0.001

if __name__ == '__main__':
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # packets = ['1']
    packets = ['razzi {}'.format(i) for i in range(100000)]
    for p in packets:
        s.sendto(p, ('172.19.179.123', 9000))
        print('{} {}'.format(int(time.time() * 1000000), p))

        # data = s.recv(1024)
        # logger.info('Got back {}'.format(data))
        time.sleep(delay)

