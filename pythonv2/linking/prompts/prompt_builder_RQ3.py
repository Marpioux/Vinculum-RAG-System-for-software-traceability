def format_class_block(class_name, class_content, is_code=False):
    """
    Formatte le bloc Java Class dans le prompt.
    - is_code = True : affiche le contenu dans un bloc code markdown
    - is_code = False : affiche comme une description simple
    """
    if is_code:
        return f"Java Class: {class_name}\nClass Code:\n```\n{class_content}\n```\n\n"
    else:
        return f"Java Class: {class_name}\nClass Description:\n{class_content}\n\n"


def baseline_prompt(reqs, class_name, class_content, is_code=False):
    """
    Prompt baseline simple sans few-shot ni CoT.
    """
    prompt_reqs = ""
    req_template = (
        "Requirement Document: {document_name}\n"
        "Content:\n{req_content}\n\n"
    )

    for req in reqs:
        prompt_reqs += req_template.format(
            document_name=req["name"],
            req_content=req["content"]
        )

    prompt_class = format_class_block(class_name, class_content, is_code)

    final_prompt = (
        f"{prompt_class}"
        f"The following is a list of related requirements retrieved from a RAG system:\n"
        f"{prompt_reqs}"
        f"Each Java class may be linked to zero, one, two, or three requirements.\n"
        f"Please analyze the class and the requirements, and determine if there is a relevant link.\n"
        f"Respond strictly using the following format (requirement ID can be UC12.txt,...):\n"
        f"RequirementID.txt -> {class_name}.java\n"
        f"or, if there is no match:\n"
        f"{class_name}.java -> None\n"
        f"\nReturn only the links in the required format. Do not include explanations."
    )

    return final_prompt


def few_shot_prompt_desc(reqs, class_name, class_content, is_code=False):
    """
    Few-shot prompting avec exemples en mode description.
    """
    examples = (
        "[EXAMPLES]\n\n"
        "Java Class: UserManager\n"
        "Class Description:\nHandles creation, update, and deletion of user accounts.\n\n"
        "Requirement Document: UC01.txt\n"
        "Content:\nThe system shall allow administrators to manage user accounts.\n\n"
        "UC01.txt -> UserManager.java\n\n"

        "Java Class: AuditLogger\n"
        "Class Description:\nLogs system events related to user actions and access.\n\n"
        "Requirement Document: UC10.txt\n"
        "Content:\nAll user actions must be logged for auditing purposes.\n\n"
        "UC10.txt -> AuditLogger.java\n\n"
        "[END OF EXAMPLES]\n\n"
    )

    return examples + baseline_prompt(reqs, class_name, class_content, is_code=False)


def few_shot_prompt_code(reqs, class_name, class_content, is_code=True):
    """
    Few-shot prompting avec exemples en mode code brut.
    """
    examples = (
        "[EXAMPLES]\n\n"
        "Java Class: PasswordHasher\n"
        "Class Code:\n```\npublic class PasswordHasher {\n    public String hash(String password) {\n        // ...bcrypt implementation...\n    }\n}\n```\n\n"
        "Requirement Document: UC05.txt\n"
        "Content:\nPasswords must be securely encrypted before storage.\n\n"
        "UC05.txt -> PasswordHasher.java\n\n"

        "Java Class: EmailSender\n"
        "Class Code:\n```\npublic class EmailSender {\n    public void sendResetLink(String email) {\n        // send email logic\n    }\n}\n```\n\n"
        "Requirement Document: UC03.txt\n"
        "Content:\nThe system shall email users a reset link upon request.\n\n"
        "UC03.txt -> EmailSender.java\n\n"
        "[END OF EXAMPLES]\n\n"
    )

    return examples + baseline_prompt(reqs, class_name, class_content, is_code=True)


def cot_prompt(reqs, class_name, class_content, is_code=False):
    """
    Prompt baseline enrichi par une instruction de chain-of-thought implicite.
    """
    base = baseline_prompt(reqs, class_name, class_content, is_code)
    step_thinking = (
        "Think step-by-step before deciding on a match. First consider the purpose of the class, "
        "then assess each requirement one by one. Only output the final matches as specified.\n\n"
    )
    return base.replace(
        "Please analyze the class and the requirements, and determine if there is a relevant link.\n",
        "Please analyze the class and the requirements, and determine if there is a relevant link.\n" + step_thinking
    )


def smart_few_shot_prompt(reqs, class_name, class_content, is_code=False):
    """
    Wrapper qui choisit automatiquement la bonne variante few-shot en fonction de is_code.
    """
    if is_code:
        return few_shot_prompt_code(reqs, class_name, class_content, is_code=True)
    else:
        return few_shot_prompt_desc(reqs, class_name, class_content, is_code=False)
