import socket
import threading
from protocol import *

class ServerThread(threading.Thread):

    #DO NOT NEED TO CHANGE
    def __init__(self, socket, protocol):
        super().__init__(name="ServerThread")
        self.socket = socket
        self.kkp = protocol

    def run(self):
        try:
            with self.socket.makefile('r') as in_stream, self.socket.makefile('w') as out_stream:
                output_line = self.kkp.process(None)
                out_stream.write(output_line + "\n")
                out_stream.flush()

                while True:
                    input_line = in_stream.readline().strip()
                    if input_line:
                        output_line = self.kkp.process(input_line)
                        out_stream.write(output_line + "\n")
                        out_stream.flush()
                        if output_line == "Bye":
                            break
            self.socket.close()
        except IOError as e:
            print(e)