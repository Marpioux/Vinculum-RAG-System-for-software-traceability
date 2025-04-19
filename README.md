# Master Thesis Experimentation

This repository contains all the code and resources used for experiments conducted as part of the master's thesis on requirement engineering. The project involves data preparation, Java code analysis, and linking requirements to code using advanced techniques like embeddings and language models.

## Project Structure

The repository is organized as follows:

### Datasets
- **`java_code/`**: Contains Java source code for different projects (`eTOUR`, `albergate`, `iTrust`).
- **`json/`**: Stores JSON files generated from Java code analysis.
- **`requirements/`**: Contains requirement documents for the projects.

### Pythonv2
- **`data_preparation/`**: Scripts for preparing data, including extracting comments, generating summaries, and creating embeddings.
  - `data_preparation_pipeline.py`: Main pipeline for data preparation.
  - `summaries/`: Contains modules for extracting comments and generating summaries.
  - `embedings/`: Contains modules for creating embeddings and storing them in Redis.
- **`evaluation/`**: Scripts for evaluating the results, including F1 metric calculation and correct match evaluation.
- **`linking/`**: Scripts for linking requirements to code using embeddings and language models.

### Java Parsing Tryout
- **`src/main/java/org/example/`**: Java code for parsing Java files and exposing an API for analysis.
  - `Parser.java`: Parses Java files and extracts classes, methods, constructors, and fields.
  - `retrieveAPI.java`: REST API for retrieving parsed Java code as JSON.
  - `Main.java`: Entry point for testing the parser.
- **`pom.xml`**: Maven configuration file for managing dependencies and building the project.

## Key Features

1. **Java Code Parsing**:
   - Uses JavaParser to analyze Java source code and extract classes, methods, constructors, and fields.
   - Exposes a REST API (`retrieveAPI.java`) to retrieve parsed data as JSON.

2. **Data Preparation**:
   - Extracts comments from Java code and generates summaries using GPT models.
   - Creates embeddings for requirements and stores them in Redis for efficient retrieval.

3. **Evaluation**:
   - Evaluates the linking between requirements and code using metrics like F1 score.

4. **Linking**:
   - Links requirements to code using embeddings and language models.

## Prerequisites

- **Java**: Requires Java 17 or higher.
- **Python**: Requires Python 3.8 or higher.
- **Redis**: For storing embeddings.
- **OpenAI API Key**: Required for GPT-based operations. Add your API key to the `.env` file.
