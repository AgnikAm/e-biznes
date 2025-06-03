from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from schemas import TextRequest, TextResponse
from model import generate_reply

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.post("/chat", response_model=TextResponse)
def chat_endpoint(data: TextRequest):
    user_input = data.message
    reply = generate_reply(user_input)
    return TextResponse(reply=reply)
