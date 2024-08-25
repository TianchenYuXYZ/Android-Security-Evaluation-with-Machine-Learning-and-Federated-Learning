import socket
import sys
from threading import Thread

class Client:
    # connect to firebase and retrieve data
    db = connect_Firebase()

    trainingInputs = getTrainingInputs(db)
    trainingOutputs = getTrainingOutputs(db)
    testingInputs = getTestingInputs(db)
    testingOutputs = getTestingOutputs(db)

    # preprocess the dataset
    scaler = StandardScaler()
    x_train = scaler.fit_transform(trainingInputs)
    x_test = scaler.transform(testingInputs)
    y_train = np.array(trainingOutputs)
    y_test = np.array(testingOutputs)

    def local_model(self):
        # tune the hyperparameters
        tuner = BayesianOptimization(
            build_model,
            objective="val_loss",
            max_trials=25,
            directory='my_dir',  # Main directory to store tuner results
            project_name='security_eval_bayesian'  # Subdirectory for this specific project

        )

        # run the hyperparameters search
        tuner.search(x_train, y_train, epochs=100, validation_split=0.3)

        # Get the optimal hyperparameters
        best_hps = tuner.get_best_hyperparameters(num_trials=1)[0]
        print(best_hps)

        # Build the model with the optimal hyperparameters
        model = tuner.hypermodel.build(best_hps)
        history = model.fit(x_train, y_train, epochs=50, validation_split=0.2)

        # Evaluate the model
        loss, mae = model.evaluate(x_test, y_test)
        print(f'Test Mean Absolute Error: {mae}')
        return model, history

    #ADD NEURAL NETWORK FOR LOCAL TRAINERS HERE
    def connect(self,host, port):
        model,history = self.local_model()
        try:
            with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as game_socket:
                game_socket.connect((host, port))
                out = game_socket.makefile('w')
                in_ = game_socket.makefile('r')
                std_in = sys.stdin

                while True:
                    from_server = in_.readline().strip()
                    if not from_server:
                        break
                    print("Server:", from_server)
                    if from_server == "Bye.":
                        break

                    from_user = model
                    #from_user contains what the client send to the server. IT IS TO DEFINE
                    if from_user:
                        print("from client:", from_user)
                        out.write(from_user + "\n")
                        out.flush()

        except socket.gaierror:
            print(f"Don't know about host {host}")
            sys.exit(1)
        except IOError as e:
            print(f"Couldn't get I/O for the connection to {host}")
            sys.exit(1)

def main():
    if len(sys.argv) != 3:
            print("Usage: python Client.py <host name> <port number>")
            sys.exit(1)

    host_name = sys.argv[1]
    port_number = int(sys.argv[2])

    client = Client()
    client.connect(host_name, port_number)

if __name__ == '__main__':
    main()