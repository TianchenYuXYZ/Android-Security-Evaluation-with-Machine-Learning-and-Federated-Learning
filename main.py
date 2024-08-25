from FirestoreConnect import *
from securityEvaluationNN_2 import *
from securityEvaluationNN_3 import *
from securityEvaluationNN_4 import *
from securityEvlauationNN_5 import *
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt


def plot_graphs(history_NN_2, mae_NN2, history_NN_3, mae_NN3, history_NN_4, mae_NN4, history_NN_5, mae_NN5):
    # Plot training MAE
    plt.figure(figsize=(12, 6))
    plt.plot(history_NN_2.history['loss'], label='NN2 Train MAE')
    plt.plot(history_NN_3.history['loss'], label='NN3 Train MAE')
    plt.plot(history_NN_4.history['loss'], label='NN4 Train MAE')
    plt.plot(history_NN_5.history['loss'], label='NN5 Train MAE')
    plt.xlabel('Epochs')
    plt.ylabel('Mean Absolute Error')
    plt.title('Training MAE over Epochs')
    plt.legend()
    plt.savefig('training_mae.png')
    plt.show()

    # Plot testing MAE
    plt.figure(figsize=(12, 6))
    plt.plot(history_NN_2.history['val_loss'], label='NN2 Test MAE')
    plt.plot(history_NN_3.history['val_loss'], label='NN3 Test MAE')
    plt.plot(history_NN_4.history['val_loss'], label='NN4 Test MAE')
    plt.plot(history_NN_5.history['val_loss'], label='NN5 Test MAE')
    plt.xlabel('Epochs')
    plt.ylabel('Mean Absolute Error')
    plt.title('Testing MAE over Epochs')
    plt.legend()
    plt.savefig('testing_mae.png')
    plt.show()

    # plot bars for Final MAE
    NN2_bar = [0, mae_NN2, 0]
    NN3_bar = [0, mae_NN3, 0]
    NN4_bar = [0, mae_NN4, 0]
    NN5_bar = [0, mae_NN5, 0]
    bar_width = 0.20

    bar1 = np.arange(len(NN2_bar))
    bar2 = [x + bar_width for x in bar1]
    bar3 = [x + bar_width for x in bar2]
    bar4 = [x + bar_width for x in bar3]

    plt.figure(figsize=(12, 6))
    plt.bar(bar1, NN2_bar, color='blue', width=bar_width, edgecolor='grey', label='NN2')
    plt.bar(bar2, NN3_bar, color='orange', width=bar_width, edgecolor='grey', label='NN3')
    plt.bar(bar3, NN4_bar, color='green', width=bar_width, edgecolor='grey', label='NN4')
    plt.bar(bar4, NN5_bar, color='red', width=bar_width, edgecolor='grey', label='NN5')

    # label graph
    plt.title('Nueral Networks and Final MAE')
    plt.xlabel('Epochs')
    plt.ylabel('Mean Absolute Error')
    plt.xticks([j + bar_width for j in range(len(NN2_bar))], ['0', '100', '200'], size=10)
    plt.legend()
    plt.savefig('NN_and_Final_MAE.png')
    plt.show()


def aggregate_models_average(models):
    num_layers = len(models[0].get_weights())
    new_weights = []
    for layer in range(num_layers):
        layer_weights = np.array([model.get_weights()[layer] for model in models])
        new_weights.append(np.mean(layer_weights, axis=0))

    return new_weights


def aggregate_models_weighted(models, weights):
    num_layers = len(models[0].get_weights())
    new_weights = []

    for layer in range(num_layers):
        layer_weights = np.array([model.get_weights()[layer] * weights[i] for i, model in enumerate(models)])
        new_weights.append(np.sum(layer_weights, axis=0) / np.sum(weights))

    return new_weights


def aggregate_model_median(models):
    num_layers = len(models[0].get_weights())
    new_weights = []

    for layer in range(num_layers):
        layer_weights = np.array([model.get_weights()[layer] for model in models])
        new_weights.append(np.median(layer_weights, axis=0))

    return new_weights

def aggregate_differents_weigths_averages(models):
    num_layers = len(models[0].get_weights())
    new_weights = []

    for layer in range(num_layers):
        layer_weights = np.array([model.get_weights()[layer] for model in models])
        average_weights = np.sum(layer_weights, axis=0) / (2 * len(models))
        new_weights.append(average_weights)
    return new_weights


def evaluate_model(model, x_test, y_test):
    loss, mae = model.evaluate(x_test, y_test)
    return mae


def main():
    # connect to firebase and retrieve data
    global aggregated_weights, aggregate_weights
    db = connect_Firebase()
    trainingInputs = getTrainingInputs(db, 1)
    trainingOutputs = getTrainingOutputs(db, 1)
    testingInputs = getTestingInputs(db, 1)
    testingOutputs = getTestingOutputs(db, 1)
    # preprocess the dataset
    scaler = StandardScaler()
    x_train = scaler.fit_transform(trainingInputs)
    x_test = scaler.transform(testingInputs)
    y_train = np.array(trainingOutputs)
    y_test = np.array(testingOutputs)

    # CODE USED FOR PROJECT 3
    # # run functions that create, train and test different nueral networks with different layers
    # history_NN_2, mae_NN2 = run_nn_C2(x_train, y_train, x_test, y_test)
    # # input()
    # history_NN_3, mae_NN3, NN3_model = run_nn_3(x_train, y_train, x_test, y_test)
    # # input()
    # history_NN_4, mae_NN4 = run_nn_4(x_train, y_train, x_test, y_test)
    # # input()
    # history_NN_5, mae_NN5 = run_nn_5(x_train, y_train, x_test, y_test)
    # # input()
    # plot_graphs(history_NN_2,mae_NN2,
    #             history_NN_3,mae_NN3,
    #             history_NN_4,mae_NN4,
    #             history_NN_5,mae_NN5)

    history_NN_3, mae_NN3, NN3_model = run_nn_3(x_train, y_train, x_test, y_test)

    x_train1, y_train1, x_test1, y_test1 = x_train, y_train, x_test, y_test

    trainingInputs2 = getTrainingInputs(db, 2)
    trainingOutputs2 = getTrainingOutputs(db, 2)
    testingInputs2 = getTestingInputs(db, 2)
    testingOutputs2 = getTestingOutputs(db, 2)
    # preprocess the dataset
    scaler = StandardScaler()
    x_train2 = scaler.fit_transform(trainingInputs2)
    x_test2 = scaler.transform(testingInputs2)
    y_train2 = np.array(trainingOutputs2)
    y_test2 = np.array(testingOutputs2)

    trainingInputs3 = getTrainingInputs(db, 3)
    trainingOutputs3 = getTrainingOutputs(db, 3)
    testingInputs3 = getTestingInputs(db, 3)
    testingOutputs3 = getTestingOutputs(db, 3)
    # preprocess the dataset
    scaler = StandardScaler()
    x_train3 = scaler.fit_transform(trainingInputs3)
    x_test3 = scaler.transform(testingInputs3)
    y_train3 = np.array(trainingOutputs3)
    y_test3 = np.array(testingOutputs3)

    trainingInputs4 = getTrainingInputs(db, 4)
    trainingOutputs4 = getTrainingOutputs(db, 4)
    testingInputs4 = getTestingInputs(db, 4)
    testingOutputs4 = getTestingOutputs(db, 4)
    # preprocess the dataset
    scaler = StandardScaler()
    x_train4 = scaler.fit_transform(trainingInputs4)
    x_test4 = scaler.transform(testingInputs4)
    y_train4 = np.array(trainingOutputs4)
    y_test4 = np.array(testingOutputs4)

    history_collection = [[0, 0, 0, 0, 0, 0, 0, 0] for i in range(4)]
    maecollection = [[0,0,0,0,0,0,0,0]for i in range(4)]
    mae_collection = [[0, 0, 0, 0] for i in range(8)]
    #create initial models with random generated weights
    init_model1 = NN3_model
    init_model2 = NN3_model
    init_model3 = NN3_model
    init_model4 = NN3_model
    
    for i in range(8):
        # train each model with a different dataset for each model
        history_collection[0][i] = init_model1.fit(x_train1, y_train1, epochs=10, batch_size=32, validation_split=0.3)
        history_collection[1][i] = init_model2.fit(x_train2, y_train2, epochs=10, batch_size=32, validation_split=0.3)
        history_collection[2][i] = init_model3.fit(x_train3, y_train3, epochs=10, batch_size=32, validation_split=0.3)
        history_collection[3][i] = init_model4.fit(x_train4, y_train4, epochs=10, batch_size=32, validation_split=0.3)

        # AGGREGATE MODELS USING averaging of all weights, weighted average, OR median
        # if i == 0 or i == 4:
        #     aggregate_weights = aggregate_models_average([init_model1, init_model2, init_model3, init_model4])
        
        # elif i == 1 or i == 5:
        #     performances = [evaluate_model(init_model1, x_test1, y_test1),
        #                     evaluate_model(init_model2, x_test2, y_test2),
        #                     evaluate_model(init_model3, x_test3, y_test3),
        #                     evaluate_model(init_model4, x_test4, y_test4)]
        #     aggregate_weights = aggregate_models_weighted([init_model1, init_model2, init_model3, init_model4],
        #                                                     [1.0 / p for p in performances])
        # elif i == 2 or i == 6:
        #     aggregate_weights = aggregate_model_median([init_model1, init_model2, init_model3, init_model4])
        
        match(i):
            case 0: # equal weights average
                aggregate_weights = aggregate_models_average([init_model1, init_model2, init_model3, init_model4])
                
            case 1: # performance higher weights average
                performances = [evaluate_model(init_model1, x_test1, y_test1),
                                evaluate_model(init_model2, x_test2, y_test2),
                                evaluate_model(init_model3, x_test3, y_test3),
                                evaluate_model(init_model4, x_test4, y_test4)]
                aggregate_weights = aggregate_models_weighted([init_model1, init_model2, init_model3, init_model4],
                                                                [1.0 / p for p in performances])
            case 2: # median
                aggregate_weights = aggregate_model_median([init_model1, init_model2, init_model3, init_model4])
            case 3: # different weights based on dataset average
                aggregate_weights = aggregate_differents_weigths_averages([init_model1, init_model2, init_model3, init_model4])
            case 4: # equal weights average
                aggregate_weights = aggregate_models_average([init_model1, init_model2, init_model3, init_model4])
            case 5: # performance higher weights average
                performances = [evaluate_model(init_model1, x_test1, y_test1),
                                evaluate_model(init_model2, x_test2, y_test2),
                                evaluate_model(init_model3, x_test3, y_test3),
                                evaluate_model(init_model4, x_test4, y_test4)]
                aggregate_weights = aggregate_models_weighted([init_model1, init_model2, init_model3, init_model4],
                                                                [1.0 / p for p in performances])
            case 6: # median
                aggregate_weights = aggregate_model_median([init_model1, init_model2, init_model3, init_model4])
            case 7: # different weights based on dataset average
                aggregate_weights = aggregate_differents_weigths_averages([init_model1, init_model2, init_model3, init_model4])

        # update weights
        for model in [init_model1, init_model2, init_model3, init_model4]:
            model.set_weights(aggregate_weights)
        # evaluate the model
        maecollection[0][i] = evaluate_model(init_model1, x_test1, y_test1)
        maecollection[1][i] = evaluate_model(init_model2, x_test2, y_test2)
        maecollection[2][i] = evaluate_model(init_model3, x_test3, y_test3)
        maecollection[3][i] = evaluate_model(init_model4, x_test4, y_test4)
        
        mae_collection[i][0] = maecollection[0][i]
        mae_collection[i][1] = maecollection[1][i]
        mae_collection[i][2] = maecollection[2][i]
        mae_collection[i][3] = maecollection[3][i]


    # PLOT LOCAL MODEL PERFORMANCE BETWEEN ROUNDS
    def plot_performance(history_collection, title):
        plt.figure(figsize=(12, 6))

        for model_index in range(len(history_collection)):
            for round_num in range(len(history_collection[model_index])):
                plt.plot(history_collection[model_index][round_num].history['val_mae'],
                          label=f'Model {model_index + 1}, Round {round_num + 1}')

        plt.xlabel('Epochs')
        plt.ylabel('Validation MAE')
        plt.title(title)
        plt.legend()
        plt.show()

    plot_performance(history_collection, 'Local Model Performance Between Rounds')

    # PLOT AGGREGATED MODELS BETWEEN ROUNDS
    plt.figure(figsize=(12, 6))
    for i in range(4):
        plt.plot(range(1, 9), [mae_collection[j][i] for j in range(8)], label=f'Model {i + 1}')
    plt.xlabel('Rounds')
    plt.ylabel('Aggregated MAE')
    plt.title('Aggregated Model Performance Between Rounds')
    plt.legend()
    plt.savefig('Aggergate_MAE_between_rounds.png')
    plt.show()
    
    # PLOT LOCAL MODEL PERFORMANCE BETWEEN ROUNDS
    # Plot training MAE
    plt.figure(figsize=(12, 6))
    plt.plot(history_collection[0][0].history['val_mae'], label='Round 1')
    plt.plot(history_collection[0][1].history['val_mae'], label='Round 2')
    plt.plot(history_collection[0][2].history['val_mae'], label='Round 3')
    plt.plot(history_collection[0][3].history['val_mae'], label='Round 4')
    plt.plot(history_collection[0][4].history['val_mae'], label='Round 5')
    plt.plot(history_collection[0][5].history['val_mae'], label='Round 6')
    plt.plot(history_collection[0][6].history['val_mae'], label='Round 7')
    plt.plot(history_collection[0][7].history['val_mae'], label='Round 8')
    plt.xlabel('Epochs')
    plt.ylabel('Mean Absolute Error')
    plt.title('Model 1 Training MAE over Epochs')
    plt.legend(loc='upper right')
    plt.savefig('training_mae_model1.png')
    plt.show()
    
    plt.figure(figsize=(12, 6))
    plt.plot(history_collection[1][0].history['val_mae'], label='Round 1')
    plt.plot(history_collection[1][1].history['val_mae'], label='Round 2')
    plt.plot(history_collection[1][2].history['val_mae'], label='Round 3')
    plt.plot(history_collection[1][3].history['val_mae'], label='Round 4')
    plt.plot(history_collection[1][4].history['val_mae'], label='Round 5')
    plt.plot(history_collection[1][5].history['val_mae'], label='Round 6')
    plt.plot(history_collection[1][6].history['val_mae'], label='Round 7')
    plt.plot(history_collection[1][7].history['val_mae'], label='Round 8')
    plt.xlabel('Epochs')
    plt.ylabel('Mean Absolute Error')
    plt.title('Model 2 Training MAE over Epochs')
    plt.legend(loc='upper right')
    plt.savefig('training_mae_model2.png')
    plt.show()
    
    plt.figure(figsize=(12, 6))
    plt.plot(history_collection[2][0].history['val_mae'], label='Round 1')
    plt.plot(history_collection[2][1].history['val_mae'], label='Round 2')
    plt.plot(history_collection[2][2].history['val_mae'], label='Round 3')
    plt.plot(history_collection[2][3].history['val_mae'], label='Round 4')
    plt.plot(history_collection[2][4].history['val_mae'], label='Round 5')
    plt.plot(history_collection[2][5].history['val_mae'], label='Round 6')
    plt.plot(history_collection[2][6].history['val_mae'], label='Round 7')
    plt.plot(history_collection[2][7].history['val_mae'], label='Round 8')
    plt.xlabel('Epochs')
    plt.ylabel('Mean Absolute Error')
    plt.title('Model 3 Training MAE over Epochs')
    plt.legend(loc='upper right')
    plt.savefig('training_mae_model3.png')
    plt.show()
    
    plt.figure(figsize=(12, 6))
    plt.plot(history_collection[3][0].history['val_mae'], label='Round 1')
    plt.plot(history_collection[3][1].history['val_mae'], label='Round 2')
    plt.plot(history_collection[3][2].history['val_mae'], label='Round 3')
    plt.plot(history_collection[3][3].history['val_mae'], label='Round 4')
    plt.plot(history_collection[3][4].history['val_mae'], label='Round 5')
    plt.plot(history_collection[3][5].history['val_mae'], label='Round 6')
    plt.plot(history_collection[3][6].history['val_mae'], label='Round 7')
    plt.plot(history_collection[3][7].history['val_mae'], label='Round 8')
    plt.xlabel('Epochs')
    plt.ylabel('Mean Absolute Error')
    plt.title('Model 4 Training MAE over Epochs')
    plt.legend(loc='upper right')
    plt.savefig('training_mae_model4.png')
    plt.show()
    
    
    # plot bars for Final MAE
    Dataset1_bar = [maecollection[0][0],maecollection[0][1],
                    maecollection[0][2],maecollection[0][3],
                    maecollection[0][4],maecollection[0][5],
                    maecollection[0][6],maecollection[0][7]]
    
    Dataset2_bar = [maecollection[1][0],maecollection[1][1],
                    maecollection[1][2],maecollection[1][3],
                    maecollection[1][4],maecollection[1][5],
                    maecollection[1][6],maecollection[1][7]]
    
    Dataset3_bar = [maecollection[2][0],maecollection[2][1],
                    maecollection[2][2],maecollection[2][3],
                    maecollection[2][4],maecollection[2][5],
                    maecollection[2][6],maecollection[2][7]]
    
    Dataset4_bar = [maecollection[3][0],maecollection[3][1],
                    maecollection[3][2],maecollection[3][3],
                    maecollection[3][4],maecollection[3][5],
                    maecollection[3][6],maecollection[3][7]]
    
    bar_width = 0.10
    
    bar1 = np.arange(len(Dataset1_bar))
    bar2 = [x + bar_width for x in bar1]
    bar3 = [x + bar_width for x in bar2]
    bar4 = [x + bar_width for x in bar3]
    
    plt.figure(figsize=(12, 6))
    plt.bar(bar1, Dataset1_bar, color='blue', width=bar_width, edgecolor='grey', label='Dataset1')
    plt.bar(bar2, Dataset2_bar, color='orange', width=bar_width, edgecolor='grey', label='Dataset2')
    plt.bar(bar3, Dataset3_bar, color='green', width=bar_width, edgecolor='grey', label='Dataset3')
    plt.bar(bar4, Dataset4_bar, color='red', width=bar_width, edgecolor='grey', label='Dataset4')

    # label graph
    plt.title('Aggregated Model MAE with each dataset over rounds')
    plt.xlabel('Round')
    plt.ylabel('Mean Absolute Error')
    plt.xticks([j + bar_width for j in range(len(Dataset1_bar))], ['1','2','3','4','5','6','7','8'], size=8)
    plt.legend()
    plt.savefig('Aggregated_MAE.png')
    plt.show()
    
                                  


if __name__ == '__main__':
    main()
