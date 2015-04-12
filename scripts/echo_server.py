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

logger = logging.getLogger()

class MyTCPHandler(SocketServer.StreamRequestHandler):
    def handle(self):
        logger.info('Packet from {}'.format(self.client_address[0]))
        self.wfile.write(self.data.upper())


if __name__ == '__main__':
    server = SocketServer.TCPServer(('172.19.177.136', 9000), MyTCPHandler)
    server.serve_forever()
