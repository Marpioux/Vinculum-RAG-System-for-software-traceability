import xml.etree.ElementTree as ET

def parse_xml_and_generate_txt(file_path, output_file):
    with open(file_path, 'r', encoding="utf-8") as f:
        xml_string = f.read()
    
    root = ET.fromstring(xml_string)
    links = root.find("links")
    
    if links is None:
        print("No links found in the XML.")
        return
    
    mappings = []

    for link in links.findall("link"):
        source_id = link.find("source_artifact_id").text.strip()
        target_id = link.find("target_artifact_id").text.strip() + ".java"
        mappings.append(f"{target_id} -> {source_id}.txt")

    # Write all mappings to a single file
    with open(output_file, "w", encoding="utf-8") as f:
        f.write("\n".join(sorted(mappings)))

    print(f"Output written to {output_file}")

# Usage
input_file = "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/Datasets/iTrust/iTrust/answer_req_javacode.xml"
output_file = "AnswerMatrixAlbergate.txt"
parse_xml_and_generate_txt(input_file, output_file)
