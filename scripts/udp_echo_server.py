import logging
import signal
import sys
import SocketServer


def signal_handler(*a):
    sys.exit(0)

signal.signal(signal.SIGINT, signal_handler)

logging.basicConfig(stream=sys.stdout,
                    level=logging.DEBUG,
                    format='%(asctime)s\t%(levelname)s\t%(message)s')
unused = 3


logger = logging.getLogger()

class MyUDPHandler(SocketServer.BaseRequestHandler):
    def handle(self):
        data = self.request[0]
        logger.info(data)

if __name__ == '__main__':
    server = SocketServer.UDPServer(('165.123.199.173', 9000), MyUDPHandler)
    server.serve_forever()
