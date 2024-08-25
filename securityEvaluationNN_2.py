"""
This code is a simple demo for building our MLP
Environment: Python 3.11, Tensorflow 2.16.1
X -> it should be all measurement facts as the input
Y -> it should be corresponding evaluation score

Two layers (64, 32)
Test Mean Absolute Error: 0.48257237672805786
"""
import tensorflow as tf
from memory_profiler import profile

# instantiating the decorator
@profile
def run_nn_C2(x_train,y_train,x_test,y_test):
    # Define the neural network model
    model = tf.keras.Sequential([
        tf.keras.layers.Dense(64, activation='relu'),
        tf.keras.layers.Dropout(0.2),
        tf.keras.layers.Dense(32, activation='relu'),
        tf.keras.layers.Dropout(0.2),
        tf.keras.layers.Dense(y_train.shape[1], activation='linear', name='output')
    ])
    
    # Compile the model
    optimizer = tf.keras.optimizers.Adam(learning_rate=0.001)
    model.compile(optimizer=optimizer, loss='mse', metrics=['mae'])
    
    # Train the model
    history = model.fit(x_train, y_train, epochs=100, batch_size=32, validation_split=0.3)
    
    # Evaluate the model on the validation set
    val_loss, val_mae = model.evaluate(x_test, y_test)
    
    print(f'Test Mean Absolute Error: {val_mae}')

    return history, val_mae
