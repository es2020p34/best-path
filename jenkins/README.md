# BestPath - Jenkins


## Registry

To execute local tests run the following :

- Start the registry: `docker run -d -p 5000:5000 --name registry registry:2`

## Create an image

1. `docker build --tag bestpath:1.0 .`
2. `docker run --publish 8000:8080 --detach --name bestpath bestpath:1.0`
3. Registry:

    3.1 

## Add image to registry



## Run

- Repository: 192.168.160.99