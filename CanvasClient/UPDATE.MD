//===================================================================

4/24/18

1. Fixed the bug that strID stores unrelated IDs which are from rubric section;

2. Fixed the bug that closeDate stores redundant one when assignment is a discussion topic;

3. The console will no longer display token after entering new token;

//===================================================================

04/20/18

1. fixed the problem that you can't run client without entering your Token first;

2. fixed the problem that client wont run if you fail to connect to Canvas;


//===================================================================

04/20/18

In order to get data from Canvas by using your own token, you have to do following:

1. Click settings button located at top left area of main window;

2. On the sub window, phase your own token into token text field;

3. Press Enter then your token will be put into the file. 

4. Restart client, then you can get data from Canvas.

Notice:

1. The token file "token.dat" has been put into ignored files list, so it wont be tracked by git. You can push your code without worry about exposing your token;

2. You may see a lot of warning("cannot write to a URLConnection if doOutput=false - call setDoOutput(true)") in console area when running client correctly. Dont worry, if you see these, that means client is running correctly. Otherwise, check your code or sent me(soberkiller) message.