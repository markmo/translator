# Translator

Microservice to translate messaging formats.

To build this service for Docker, run

    sbt docker:publishLocal
    
To run the Docker service, run the provided bash script

    ./docker-run.sh

This executes

    docker run -p 49167:8080 -d translator:1.0

The service will be available on port 49167

To attach to the shell of the running daemon

    docker exec -i -t <container-id> /bin/bash

The list of containers can be found using

    docker ps -a

From within the daemon shell, test the service using

    curl -H "content-Type: application/json" -X POST -d '{"bot_id":"522ea4e8-2057-451c-ae4c-2681f74917bc","dialog_id":"4ca761f0-9c70-4b63-b48e-9f23f56e017b","message":{"text":["I'm looking forward to helping you today."],"context":{"dialog_in_progress":false,"system":{"dialog_request_counter":1,"dialog_turn_counter":1,"dialog_stack":["root"]},"conversation_id":"aa94e06a-b6f6-452c-a9d4-fb4dcf44d9ec"},"intents":[{"confidence":1.0,"intent":"Help-Greetings"}],"log_data":{"wva_top_intent":{"confidence":1.0,"intent":"Help-Greetings"},"show_intent_link":true,"intents":[{"confidence":1.0,"intent":"Help-Greetings"}],"entities":[],"first_call_log":false},"node_position":"ROOT"}}' http://localhost:8080/translate

    curl -H "content-Type: application/json" -X POST -d '{"bot_id":"522ea4e8-2057-451c-ae4c-2681f74917bc","dialog_id":"4ca761f0-9c70-4b63-b48e-9f23f56e017b","message":{"text":["Please select one of the following options."],"context":{"CurrentWorkspace":"Custom Workspace","dialog_in_progress":false,"system":{"dialog_request_counter":2.0,"dialog_turn_counter":2.0,"dialog_stack":[{"dialog_node":"Complaint"}],"_node_output_map":{"Complaint":[0.0]}},"wva":{"intents":[{"intent":"Complaints-Misc","confidence":0.9999999931997042}]},"conversation_id":"da5912f7-d974-4790-b46d-3ca85ecdd434"},"layout":{"name":"choose"},"inputvalidation":{"oneOf":["Chat with an Agent","Lodge a Complaint"]},"intents":[{"confidence":0.9999999931997042,"intent":"Complaints-Misc"}],"log_data":{"wva_top_intent":{"confidence":0.9999999931997042,"intent":"Complaints-Misc"},"show_intent_link":true,"intents":[{"confidence":0.9999999931997042,"intent":"Complaints-Misc"}],"default_voyager_workspace":false,"entities":[],"first_call_log":false},"node_position":"ROOT"}}' http://localhost:8080/translate

From outside

    curl -H "content-Type: application/json" -X POST -d '{"bot_id":"522ea4e8-2057-451c-ae4c-2681f74917bc","dialog_id":"4ca761f0-9c70-4b63-b48e-9f23f56e017b","message":{"text":["I'm looking forward to helping you today."],"context":{"dialog_in_progress":false,"system":{"dialog_request_counter":1,"dialog_turn_counter":1,"dialog_stack":["root"]},"conversation_id":"aa94e06a-b6f6-452c-a9d4-fb4dcf44d9ec"},"intents":[{"confidence":1.0,"intent":"Help-Greetings"}],"log_data":{"wva_top_intent":{"confidence":1.0,"intent":"Help-Greetings"},"show_intent_link":true,"intents":[{"confidence":1.0,"intent":"Help-Greetings"}],"entities":[],"first_call_log":false},"node_position":"ROOT"}}' http://localhost:49167/translate

    curl -H "content-Type: application/json" -X POST -d '{"bot_id":"522ea4e8-2057-451c-ae4c-2681f74917bc","dialog_id":"4ca761f0-9c70-4b63-b48e-9f23f56e017b","message":{"text":["Please select one of the following options."],"context":{"CurrentWorkspace":"Custom Workspace","dialog_in_progress":false,"system":{"dialog_request_counter":2.0,"dialog_turn_counter":2.0,"dialog_stack":[{"dialog_node":"Complaint"}],"_node_output_map":{"Complaint":[0.0]}},"wva":{"intents":[{"intent":"Complaints-Misc","confidence":0.9999999931997042}]},"conversation_id":"da5912f7-d974-4790-b46d-3ca85ecdd434"},"layout":{"name":"choose"},"inputvalidation":{"oneOf":["Chat with an Agent","Lodge a Complaint"]},"intents":[{"confidence":0.9999999931997042,"intent":"Complaints-Misc"}],"log_data":{"wva_top_intent":{"confidence":0.9999999931997042,"intent":"Complaints-Misc"},"show_intent_link":true,"intents":[{"confidence":0.9999999931997042,"intent":"Complaints-Misc"}],"default_voyager_workspace":false,"entities":[],"first_call_log":false},"node_position":"ROOT"}}' http://localhost:8080/translate
    
As a convenience to stop and remove the Docker container and image

    ./docker-remove.sh <container-id>

Use Postman to test the API. Alternatively, you can use the Swagger UI at

    http://localhost:49167/swagger

Open the `translate` operation. Copy the following text into the body

    {"bot_id":"522ea4e8-2057-451c-ae4c-2681f74917bc","dialog_id":"4ca761f0-9c70-4b63-b48e-9f23f56e017b","message":{"text":["Please select one of the following options."],"context":{"CurrentWorkspace":"Custom Workspace","dialog_in_progress":false,"system":{"dialog_request_counter":2.0,"dialog_turn_counter":2.0,"dialog_stack":[{"dialog_node":"Complaint"}],"_node_output_map":{"Complaint":[0.0]}},"wva":{"intents":[{"intent":"Complaints-Misc","confidence":0.9999999931997042}]},"conversation_id":"da5912f7-d974-4790-b46d-3ca85ecdd434"},"layout":{"name":"choose"},"inputvalidation":{"oneOf":["Chat with an Agent","Lodge a Complaint"]},"intents":[{"confidence":0.9999999931997042,"intent":"Complaints-Misc"}],"log_data":{"wva_top_intent":{"confidence":0.9999999931997042,"intent":"Complaints-Misc"},"show_intent_link":true,"intents":[{"confidence":0.9999999931997042,"intent":"Complaints-Misc"}],"default_voyager_workspace":false,"entities":[],"first_call_log":false},"node_position":"ROOT"}}

Enter `1234` for the sender.

Click on the small "Try it out!" button under the "Response Messages" section. In the "Response Body" further below,
a 'quick reply' message formatted for Facebook Messenger should be shown.
