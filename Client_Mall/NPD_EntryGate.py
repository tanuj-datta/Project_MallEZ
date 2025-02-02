import cv2
import numpy as np
import easyocr
import time
import mysql.connector as sql
import time
import DatabaseInitializer as dbi
def most_frequent(List):
    return max(set(List) , key = List.count)

# Load the cascade for detecting number plates
plate_cascade = cv2.CascadeClassifier('.\haarcascade_plate_number.xml')

#get wifi password
with open("./dbp.txt",'r') as dbpf:
    pw = dbpf.read()
    dbpf.close()

#Database Connector Object
try:
    conn=sql.connect(host='localhost',user='root',passwd=pw,database='mallDatabase',charset='utf8')
    cur = conn.cursor()
except:
    dbi.create_database(pw)
    time.sleep(4)
    conn=sql.connect(host='localhost',user='root',passwd=pw,database='mallDatabase',charset='utf8')
    cur = conn.cursor()

# Start the webcam
cap = cv2.VideoCapture(0)

# Load the EasyOCR model
reader = easyocr.Reader(['en'])
nlist = []

while True:
    # Read each frame from the webcam
    ret, frame = cap.read()

    # Convert the frame to grayscale
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    # Detect number plates in the frame
    plates = plate_cascade.detectMultiScale(gray, 1.1, 4)

    # Draw a rectangle and display the number for each detected number plate
    for (x, y, w, h) in plates:
        roi_gray = gray[y:y+h, x:x+w]
        roi_color = frame[y:y+h, x:x+w]

        # Use EasyOCR to extract the number from the roi
        text = reader.readtext(roi_gray)

        if text:
            text = text[0][1]
            nlist.append(text)
        else:
            text = ""

        # Draw a rectangle around the number plate and display the number
        cv2.rectangle(frame, (x, y), (x + w, y + h), (255, 0, 0), 2)
        cv2.putText(frame, text, (x, y-10), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 0, 0), 2)
        if(len(nlist)>20):
            uid = most_frequent(nlist)
            print(uid)
            now = int(time.time())
            print(now)
            time.sleep(10)
            nlist.clear()
            uid = uid.replace(" ","")
            uid = uid.lower()
            try:
                st = "INSERT INTO data(user_id,time) values('{}','{}')".format(uid,now)
                cur.execute(st)
                conn.commit()
                print("Data added to Database, open the gate!")
            except:
                print("Failed To Add Data to database! Please Try Again")
            
    # Display the resulting frame
    cv2.imshow('Number Plate Detection', frame)

    # Break the loop if the 'q' key is pressed
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# Release the webcam and destroy all windows
cap.release()
cv2.destroyAllWindows()

