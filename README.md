# Master Thesis Experimentation

This repository contains all the code I used for my experiments, including data preparation (AST and API for Java code), ...

# Use of Pharo
## Instalation

  Todo

## Start server 

```smalltalk
"restart routes"
TLWebserver teapot 
	removeAllDynamicRoutes.
"rebuild routes from pragma method"
TLRESTAPIBuilder buildAPI.
"start the server"
TLWebserver start.
```
