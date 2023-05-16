#!/bin/bash

# Run the Python script in the background
python3 harvest-10s.py &

# Sleep for 10 seconds
sleep 10

# Send the interrupt signal (Ctrl+C)
kill $!

