from model import *
from data import *
import sys

rnn = torch.load('C:\\Users\\KV-User\\Desktop\\surname_nationality\\Kursova_2\\server\\char-rnn-classification6.pt')

# Just return an output given a line
def evaluate(line_tensor):
    hidden = rnn.initHidden()
    
    for i in range(line_tensor.size()[0]):
        output, hidden = rnn(line_tensor[i], hidden)
    
    return output

def predict(line, n_predictions=1):
    output = evaluate(Variable(lineToTensor(line)))

    # Get top N categories
    topv, topi = output.data.topk(n_predictions, 1, True)
    predictions = []

    for i in range(n_predictions):
        value = topv[0][i]
        category_index = topi[0][i]
        print((all_categories[category_index]))
        predictions.append([value, all_categories[category_index]])

    return predictions

if __name__ == '__main__':
    # Check if there is at least one command-line argument
    if len(sys.argv) > 1:
        predict(sys.argv[1])
    else:
        print("Usage: python predict.py some_input")