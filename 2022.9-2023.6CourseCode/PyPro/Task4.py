import random
from selenium.webdriver.common.by import By
from selenium.webdriver.support.expected_conditions import *
import time
from main import login
from main import driver
from main import wait


# f5 refresh
from pymouse import PyMouse
from pykeyboard import PyKeyboard
m = PyMouse()
k = PyKeyboard()


login()
#---------------------------------------------------------------------------------------
# Task 4: Place the order to the limited items
def buy_PS5(product_name:str):
    def search_item(product_name:str):
        # search
        search_text = driver.find_element(By.NAME,'q')
        search_text.send_keys(product_name)
        search = driver.find_element(By.XPATH,"//button[@class='p-2 btn btn-outline-success']")
        search.click()
    def add_to_cart():
        # choose product
        card_bodys = wait.until(presence_of_all_elements_located((By.CLASS_NAME, "card-body")))
        for card_body in card_bodys:
            #The product link is under tag 'a'
            prod = card_body.find_element(By.TAG_NAME,'a')
        prod.click()
        # add
        add = driver.find_element(By.XPATH,"//button[@class='btn-block btn btn-primary']")
        # 判断是否可下单
        while (add.is_enabled()== False):
            driver.refresh()            
            print('False & refresh')
        add.click()
        wait
    def place_order():
        # check out
        chck = driver.find_element(By.XPATH,"//button[@class='btn-block btn btn-primary']")
        chck.click()
        # information
        wait
        text1 = driver.find_element(By.ID,'address')
        text1.send_keys('address')
        text2 = driver.find_element(By.ID,'city')
        text2.send_keys('city')
        text3 = driver.find_element(By.ID,'postalCode')
        text3.send_keys(111111)
        text4 = driver.find_element(By.ID,'country')
        text4.send_keys('country')
        wait
        # continue
        con1 = driver.find_element(By.XPATH,"//button[@class='btn btn-primary']")
        con1.click()
        # con&continue
        con2 = driver.find_element(By.XPATH,"//button[@class='btn btn-primary']")
        con2.click()
        # pay
        pay = driver.find_element(By.XPATH,"//button[@class='btn-block btn btn-primary']")
        pay.click()
    def sth_appear():
        try:
            driver.find_element(By.CLASS_NAME, "card-body")
        except NoSuchElementException:
#             # 打印异常信息
#             print(NoSuchElementException)
            # 发生异常，说明页面中未找到该元素，返回False
            return False
        else:
            # 无异常，说明在页面中找到了该元素，返回True
            return True 

    search_item(product_name)
    while (sth_appear() == False):
        print('nothing here')
        t = random.randint(0,1) # 页面停留时间随机，防止被和谐
        time.sleep(t)
        driver.get('http://10.113.178.219/search')
        print ('refresh successful')
        search_item(product_name)
    print('Appear!')
    add_to_cart()
    place_order()
    print('Success!')

# buy_PS5('Merge')
# buy_PS5('SAO')
# buy_PS5('SpaceX')
# buy_PS5('Anya')
# buy_PS5('iphone')