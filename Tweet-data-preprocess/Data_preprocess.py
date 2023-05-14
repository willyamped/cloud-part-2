import ijson
import pandas as pd
from collections import Counter


T1 = list()
T2 = list()
States = ["New South Wales","Victoria","Queensland","South Australia", 
          "Western Australia","Tasmania","Northern Territory","Australian Capital Territory"]

# requirement word for covid and lockdown
r_w_covid = ['covid', 'Covid','COVID']
r_w_lockdown = ['lockdown', 'Lockdown','LOCKDOWN']

# Count the number of city that tweet content shows covid and lockdown separately
def preprocess(input_file):
    
    with open(input_file, "r",encoding='UTF-8') as f:
        objects = ijson.items(f, "item")
        obj = next(objects, False)
        
        
        while obj != False:
            # extract tweet content
            text1 = obj["data"]["text"]
            # extract the location 
            city = obj['includes']['places'][0]['full_name']
            # match if requirement words for covid show in tweet content
            if any(word in text1 for word in r_w_covid):
            # Store the tweet's city name if matched           
                for i in States:
                    if i in city:
                        T1.append(i)
                    else:
                        T1.append("Other Territories")
            # match if requirement words for lockdown show in tweet content
            elif any(word in text1 for word in r_w_lockdown):
                # Store the tweet's city name if matched 
                for i in States:
                    if i in city:
                        T2.append(i)
                    else:
                        T2.append("Other Territories")
        
            obj = next(objects, False)
        # count the number of city
        counts1 = Counter(T1)
        counts2 = Counter(T2)
    return counts1, counts2

def main():
    # Get file
    input_file = "twitter-huge.json"

    # Preprocess data
    result1, result2 = preprocess(input_file)
    
    result_df1 = pd.DataFrame.from_records(list(dict(result1).items()), columns=['States','COVID-19 mentioned'])
    result_df2 = pd.DataFrame.from_records(list(dict(result2).items()), columns=['States','Lockdown mentioned'])
    

    # Store data
    result_df1.to_csv('C19_states.csv')
    result_df2.to_csv('Lockdown_states.csv')

# Run the actual program    
if __name__ == "__main__":
    main()
