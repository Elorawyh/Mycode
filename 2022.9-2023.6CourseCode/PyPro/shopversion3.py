# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
import functools
import json
import time

import requests
import ast
if __name__ == '__main__':
    # 抢票 Version3
    limitproduct = 'Pak The Merge'
    #limitproduct = 'APPLE WATCH SERIES 5'
    #limitproduct = 'GEFORCE RTX 4090 LIQUID COOLING'
    loginurl = 'http://10.113.178.219/api/users/login'
    data = {"email": "mc@um.edu.mo","password": "mc"}
    loginresponse = requests.post(loginurl, json=data)
    token = loginresponse.text.split('token')[1].split('"')[2]
    print(token)
    search_url = 'http://10.113.178.219/api/products?keyword=' + limitproduct
    while True:
        searchresult = requests.get(search_url)
        dicresult = json.loads(searchresult.text)
        if len(dicresult['products']) > 0:
            time_start = time.time()
            productId = dicresult['products'][0]['_id']
            productname = dicresult['products'][0]['name']
            productimage = dicresult['products'][0]['image']
            price = dicresult['products'][0]['price']
            countInStock = dicresult['products'][0]['countInStock']
            orderdata = {"orderItems":[{"product":productId,"name":productname,"image":"/images/random(83).jpg","price":price,"countInStock":countInStock,"qty":1}],"shippingAddress":{"address":'11',"city":'1',"postalCode":'1',"country":'1'},"paymentMethod":"PayPal","itemsPrice":price,"shippingPrice":"0.00","taxPrice":"51.60","totalPrice":"395.59"}
            headers = {"Authorization": "Bearer "+token}
            response = requests.post('http://10.113.178.219/api/orders',json=orderdata,headers=headers)
            #print(response.text)
            time_end = time.time()
            time_sum = time_end - time_start
            print(time_sum)
            break
        time.sleep(0.2)
