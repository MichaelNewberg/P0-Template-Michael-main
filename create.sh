# this bash script will be used to create my sports docker image
# I will use this script instead of manually entering commands so i dont actually make a spelling mistake

# Builds image
docker build -t michaelnewberg/p1:new .
# Pushes image to dockerhub
docker push michaelnewberg/p1:new
# Composes container using image
docker compose up -d
