import time
import socket
import logging
import sys


logging.basicConfig(stream=sys.stdout,
                    level=logging.DEBUG,
                    format='%(asctime)s\t%(levelname)s\t%(message)s')

logger = logging.getLogger()

delay = 0.001


if __name__ == '__main__':
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    packets = ['1']
    # packets = [str(i) * 50 for i in range(10000)]
    for p in packets:
        s.sendto(p, ('172.19.177.132', 9000))
        logger.info(p)

        # data = s.recv(1024)
        # logger.info('Got back {}'.format(data))
        time.sleep(delay)

