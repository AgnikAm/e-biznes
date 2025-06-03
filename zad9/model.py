from transformers import pipeline

generator = pipeline("text2text-generation", model="google/flan-t5-base")

def generate_reply(user_input: str) -> str:
    prompt = f"Answer this question: {user_input}"
    output = generator(
        prompt,
        max_length=60,
        num_return_sequences=1,
        do_sample=True,
        temperature=0.7,
        top_p=0.95
    )
    return output[0]['generated_text'].strip()
