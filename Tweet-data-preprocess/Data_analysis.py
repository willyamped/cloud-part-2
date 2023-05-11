import ijson
import pandas as pd
from collections import Counter


T1 = list()
States = ["New South Wales","Victoria","Queensland","South Australia", 
          "Western Australia","Tasmania","Northern Territory","Australian Capital Territory"]

requirement_word = ['covid', 'Covid','COVID']

def preprocess(input_file):
    
    with open(input_file, "r",encoding='UTF-8') as f:
        objects = ijson.items(f, "item")
        obj = next(objects, False)
        
        requirement_word = ['covid', 'Covid','COVID']
        while obj != False:
            text1 = obj["data"]["text"]
            
            city = obj['includes']['places'][0]['full_name']
            # Check if text contant word covid
            if any(word in text1 for word in requirement_word):
            # Count tweets that contain word covid in each state
                for i in States:
                    if i in city:
                        T1.append(i)
                    else:
                        T1.append("Other Territories")
        
            obj = next(objects, False)
    
        counts = Counter(T1)
    return counts

def main():
    # Get file
    input_file = "twitter-huge.json"

    # Preprocess data
    result = preprocess(input_file)
    
    result_df = pd.DataFrame.from_records(list(dict(result).items()), columns=['States','COVID-19 mentioned'])
    

    # Store data
    result_df.to_csv('C19_states.csv')

# Run the actual program    
if __name__ == "__main__":
    main()
