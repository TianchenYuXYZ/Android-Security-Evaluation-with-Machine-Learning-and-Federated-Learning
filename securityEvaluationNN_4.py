"""
This code is a simple demo for building our MLP
Environment: Python 3.11, Tensorflow 2.16.1
X -> it should be all measurement facts as the input
Y -> it should be corresponding evaluation score

Tune the with 4 layers (256, 128, 64, 32)

Test Mean Absolute Error: 0.3958062529563904
"""

import tensorflow as tf
from memory_profiler import profile

@profile
def run_nn_4(x_train,y_train,x_test,y_test):
    # define the neural network
    model = tf.keras.Sequential([
        tf.keras.layers.Dense(256, activation='relu'),
        tf.keras.layers.Dropout(0.2),
        tf.keras.layers.Dense(128, activation='relu'),
        tf.keras.layers.Dropout(0.2),
        tf.keras.layers.Dense(64, activation='relu'),
        tf.keras.layers.Dropout(0.2),
        tf.keras.layers.Dense(32, activation='relu'),
        tf.keras.layers.Dense(y_train.shape[1], activation='linear', name='output')
    ])
    
    # compile the model
    optimizer = tf.keras.optimizers.Adam(learning_rate=0.001)
    model.compile(optimizer=optimizer, loss='mse', metrics=['mae'])
    
    # train the model
    history = model.fit(x_train, y_train, epochs=100, batch_size=32, validation_split=0.3)
    
    # evaluate the model
    loss, mae = model.evaluate(x_test, y_test)
    
    print(f'Test Mean Absolute Error: {mae}')

    return history, mae
