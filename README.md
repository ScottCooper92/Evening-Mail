Evening Mail
-------
This is an Android application I developed to consume multiple RSS feeds for the same news website. The RSS feeds have since been removed rendering this application useless.
It's being hosted here as a guide to other developers looking for a news based Android app.

There is a large amount of HTML parsing that is specific to the HTML returned from the RSS Feed, much of this parsing should be stripped out or ignored.

This app makes use of several libraries:
Retrofit
Dagger
Butterknife
Otto
Picasso
Realm

The code is loosely structured around the MVP pattern but currently contains too much business logic within the presenters. 
Eventually I would like to use MVP in conjunction with the repository pattern to have a clear separation of concerns.

To Do
-------
Move business logic out of the presenters and into the domain layer.
Migrate the app to a working RSS Feed to better demonstrate it's functionality.


License
-------
    The MIT License (MIT)

    Copyright (c) 2016 Scott Cooper

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
