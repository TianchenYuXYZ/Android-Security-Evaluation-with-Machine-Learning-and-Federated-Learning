"""
This code is a simple demo for building our MLP
Environment: Python 3.11, Tensorflow 2.16.1
X -> it should be all measurement facts as the input
Y -> it should be corresponding evaluation score

Tune the with 4 layers (256, 128, 64, 32) & Using Keras Tuner to adjust hyperparameters (such as the number of
neurons in each layer, the dropout rates, learning rates, etc.)
Using Bayesian Optimization

Test Mean Absolute Error: 0.38431960344314575
"""

import tensorflow as tf
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from FirestoreConnect import *
from keras_tuner.tuners import BayesianOptimization

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


# define the neural network
def build_model(hp):
    model = tf.keras.Sequential()
    model.add(tf.keras.layers.Dense(units=hp.Int('units1', min_value=32, max_value=512, step=32), activation='relu'))
    model.add(tf.keras.layers.Dropout(hp.Float('dropout1', 0, 0.5, step=0.1)))
    model.add(tf.keras.layers.Dense(units=hp.Int('units2', min_value=32, max_value=512, step=32), activation='relu'))
    model.add(tf.keras.layers.Dropout(hp.Float('dropout2', 0, 0.5, step=0.1)))
    model.add(tf.keras.layers.Dense(units=hp.Int('units3', min_value=32, max_value=512, step=32), activation='relu'))
    model.add(tf.keras.layers.Dropout(hp.Float('dropout3', 0, 0.5, step=0.1)))
    model.add(tf.keras.layers.Dense(units=hp.Int('units4', min_value=32, max_value=512, step=32), activation='relu'))
    model.add(tf.keras.layers.Dense(y_train.shape[1]))  # Output layer
    # model.compile(optimizer=hp.Choice('optimizer', values=['adam', 'sgd']), loss='mse', metrics=['mae'])
    model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=hp.Float('lr', 1e-5, 1e-2, sampling='LOG')),
                  loss='mse', metrics=['mae'])
    return model


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