from selenium.webdriver.common.by import By
from selenium.webdriver.support.expected_conditions import *
from main import driver
from main import wait
from main import login


# login()

#---------------------------------------------------------------------------------------
# Task 1: place the order
# implement the following function and run
def place_the_order(product_name:str):
    def search_item(product_name:str):
        # search
#         butt_tog = driver.find_element(By.NAME,'Toggle navigation')
#         butt_tog.click()
        search_text = driver.find_element(By.NAME,'q')
        search_text.send_keys(product_name)
        search = driver.find_element(By.XPATH,"//button[@class='p-2 btn btn-outline-success']")
        search.click()
#         time.sleep(operate_delay)
    def add_to_cart():
        # choose product
        card_bodys = wait.until(presence_of_all_elements_located((By.CLASS_NAME, "card-body")))
        for card_body in card_bodys:
            #The product link is under tag 'a'
            prod = card_body.find_element(By.TAG_NAME,'a')
        prod.click()
        # add
        add = driver.find_element(By.XPATH,"//button[@class='btn-block btn btn-primary']")
        add.click()
#         time.sleep(operate_delay)
        wait
        wait
    def place_order():
#         # cart page
#         driver.get('http://10.113.178.219/cart')
        # check out
        chck = driver.find_element(By.XPATH,"//button[@class='btn-block btn btn-primary']")
        chck.click()
        # information
        text1 = driver.find_element(By.ID,'address')
        text1.send_keys('address')
        text2 = driver.find_element(By.ID,'city')
        text2.send_keys('city')
        text3 = driver.find_element(By.ID,'postalCode')
        text3.send_keys(111111)
        text4 = driver.find_element(By.ID,'country')
        text4.send_keys('country')
#         time.sleep(operate_delay)
        wait
        # continue
        con1 = driver.find_element(By.XPATH,"//button[@class='btn btn-primary']")
        con1.click()
        # con&continue
        con2 = driver.find_element(By.XPATH,"//button[@class='btn btn-primary']")
        con2.click()
#         time.sleep(operate_delay)
        # pay
        pay = driver.find_element(By.XPATH,"//button[@class='btn-block btn btn-primary']")
        pay.click()


    search_item(product_name)
    add_to_cart()
    place_order()

# place_the_order('IPHONE 11 PRO 256GB MEMORY')