"""from protocol_fields import *
import tensorflow as tf
import tensorflow_federated as tff
class Protocol(ProtocolFields):
    #NEED TO CHANGE
    def __init__(self):
        self.i=0
        self.models = []

    def process(self, str_input):
        #WILL CONTAIN THE MODEL FOR THE FEDERATED LEARNING
        #RETURN THE RESPONSE OF THE SERVER
        self.i+=1
        self.models.append(str_input)
        if self.i>5:
            models = self.models[:]
            train = tff.aggregators.federated_sample(models[0],models[1],models[2],models[3],models[4])
            state = train.initialize()
            for _ in range(5):
                state,metrics = train.next(state, train_data)
                print(metrics.loss)
            return state

"""