# Android Security Evaluation with Machine Learning and Federated Learning
This repository showcases a series of projects focused on evaluating Android device security through advanced machine learning and federated learning techniques. Developed as part of a capstone course at the Golisano College of Computing and Information Sciences, Rochester Institute of Technology, these projects progressively integrate machine learning models to enhance the accuracy and scalability of security evaluations.

## Project Overview
### Phase 1: Foundation with Expert Systems
The first phase of the project established a baseline by developing an expert system that assesses Android device security. This system used a rule-based approach to evaluate factors such as app permissions, OS version, and network security settings, providing a foundational understanding of the security landscape.

### Phase 2: Advanced Security Evaluation Using Machine Learning
Building on the initial expert system, the second phase introduced machine learning to automate and refine the security evaluation process. A Multi-Layer Perceptron (MLP) model, implemented using TensorFlow, was trained to predict security scores based on the same input data used in the expert system. This phase involved extensive experimentation with various neural network architectures:

- 2-Layer MLP: Basic architecture to test initial model performance.
- 3-Layer MLP: Achieved the best balance between accuracy and resource consumption, with a Mean Absolute Error (MAE) just under 40%.
- 4 and 5-Layer MLPs: Explored deeper networks but with diminishing returns in accuracy and increased memory usage.
The machine learning models not only replicated the expert system’s outputs but also improved upon them by learning complex patterns in the data, thereby enhancing the precision of security evaluations.

### Phase 3: Federated Learning for Privacy-Preserving Security Evaluation
The final phase pushed the boundaries of the project by implementing federated learning—a cutting-edge approach that allows multiple devices to collaboratively train models without sharing raw data. This phase addressed the growing need for privacy-preserving machine learning in security-sensitive environments.

Key aspects of the federated learning implementation include:

- Local Model Training: Several local models were trained independently on different datasets, each representing variations of the expert system’s rules.
- Aggregation Strategies: Various strategies were employed to combine the local models into a global model, including:
  - Equal Weights Average: A straightforward approach giving equal importance to all local models.
  - Performance-Based Weighted Average: Prioritized models that demonstrated lower MAE on their validation sets, leading to more accurate global models.
  - Dataset Size-Based Weighted Average: Leveraged larger datasets to guide the global model, assuming these models had learned more generalizable features.
  - Median Aggregation: A robust approach to mitigate the impact of outliers by focusing on the median values of the models’ weights.
Through iterative rounds of local training and global aggregation, the federated learning models demonstrated significant improvements in accuracy while maintaining data privacy. The performance-based weighted average strategy proved particularly effective, consistently reducing the MAE across rounds and leading to a highly accurate global model.

## Key Features
Machine Learning Integration: Transitioned from rule-based to data-driven security evaluations using TensorFlow-based MLP models. Experimentation with different neural network depths provided insights into optimizing model complexity and resource usage.
Federated Learning Implementation: Enabled decentralized training across multiple devices, preserving data privacy while improving model accuracy. The project highlights the effectiveness of federated learning in security-critical applications.
Performance Optimization: Emphasis on reducing MAE while managing memory consumption and computational efficiency, especially in resource-constrained environments like mobile devices.
## Results Summary
Machine Learning Models: The 3-layer MLP emerged as the optimal architecture, providing high accuracy (MAE < 40%) with efficient memory usage (~506 MiB during testing).
Federated Learning: The performance-based aggregation strategy led to the best results, with steady improvements in the global model’s accuracy over multiple rounds of training. This approach demonstrates the potential of federated learning in enhancing security evaluations while safeguarding user privacy.
