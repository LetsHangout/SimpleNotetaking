SimpleNotetaking
================

Hi! Finally got the ball rolling on this thing. 

I have just initialized a basic project in eclipse without any changes as of now.
Please add things to do here.


To Do:
- [x] Create a git repo
- [x] [Readup on github readme formatting](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet)
- [ ] Get everyone setup with basic project
- [ ] Organize a skype call?
- [ ] Plan details for a project



To pull the project files:

$ git clone https://github.com/LetsHangout/SimpleNotetaking

Then import to eclipse


To pull any changes:

$ git pull origin master


To push changes:

$ git add [file that was changed]

$ git commit -m ['message']

$ git push origin [branch]


If you want to learn more about using git, this book will do a lot better explaining than I can: http://git-scm.com/book/en


Also, If you see I'm doing something wrong please correct me! This is my first time setting up a project!

---

#### Git Commits with Eclipse

You can use the [Egit Eclipse plugin](http://www.eclipse.org/egit/download/) to more easily manage which files you want to
commit.  For instance, you never want to commit your personal .settings folder as they are specific to your version
of eclipse and others may not want to use the same settings as you.

To commit, right click the project go to Team -> Commit
![readme_android1](http://imgur.com/nrrf2BN.png "Committing through Eclipse")

It may ask you to setup some configuration stuff, just enter a username and email and hit next

In the commit changes window, select only the files you want to commit and leave a short comment of what changes were made
![readme_android1](http://imgur.com/Qb3QyaA.png "Committing through Eclipse")

Click Commit or Commit and Push.  I've always had problems with Commit and Push, so I usually just do a Commit in eclipse
and then 'git push' through Git Bash.

Just as an additional side note, if you don't know the difference between commit
and push, commit is basically saving to your 'local' repository on your machine and push takes that 'local' repository
and commits or 'pushes' it to the github server so others can access it.  No one will be able to see the changes until
your commited changes have been pushed to the server.
