4/12/18

branch: dev4

In this ver, I added download class called downloadFile.java. By putting its methods into main function, you can get single submission from canvas, according to previous responses from Canvas.In the main function, there are two steps you need to do. First, declare a obj for download. Second, set up url by using method .setAPI(String). Last thing is method .setDownload(String filename). filename is the name for the submission you downloaded. You can get it from previous response from Canvas or rename it what ever you want. 

===================================

4/8/18

branch: dev3

This version is a demo for demonstration. It can get courses and students information for current user who put token in application. At beginning, I thought users should be put into API. However, seems like you cant get information for all users in one course. You can get specific group of users information by adding certain key words into endpoints. For this demo,I just get students information. One more thing, you can only see student id in server and student name if you dont have high level role on your own.

I have no idea about how to design the GUI.  

===================================

4/2/18

branch: dev2

the problem was solved. The reason is I didn't give correct assignment id. In this version, I can either get information what I want from Canvas, or grade people's assignment. I think I can get only one single assignment from Canvas. Will do it in the next version!

===================================

4/1/18

branch: dev

I tried to get assignments from Dov. However, the response was 404 Not Found, which does not match the response I got from PosterMan the day before yesterday. Maybe they change server after 9pm every day for some reasons. I will try tomorrow.   