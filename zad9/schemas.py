from pydantic import BaseModel

class TextRequest(BaseModel):
    message: str

class TextResponse(BaseModel):
    reply: str
