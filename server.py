import socket
import sys
import threading
from ServerThread import *
class Server:

    #DO NOT HAVE TO CHANGE
    @staticmethod
    def main(args):
        port_number = int(args)
        listening = True

        try:
            with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server_socket:
                server_socket.bind(('', port_number))
                server_socket.listen()
                print(f"Server listening on port {port_number}")

                while listening:
                    client_socket, _ = server_socket.accept()
                    kkp = Protocol()
                    ServerThread(client_socket, kkp).start()

        except IOError as e:
            print(f"Could not listen on port {port_number}")
            print(e)
            sys.exit(-1)



if __name__ == "__main__":
    Server.main(sys.argv[1])