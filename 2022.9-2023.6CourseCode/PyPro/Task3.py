from selenium.webdriver.support.expected_conditions import *
from Task2 import Find_top_5
from Task1 import place_the_order
from main import login
from main import driver

login()
#---------------------------------------------------------------------------------------
# Task 3: Place the order to the top-5 products you found in task-2
def order_top5():
    top_5_result = Find_top_5()
    # print(top_5_result)
    for product in top_5_result:
        driver.get('http://10.113.178.219/search')
        place_the_order(product['name'])

order_top5()