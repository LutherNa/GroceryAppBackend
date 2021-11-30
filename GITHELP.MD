##Quick guide to steps for collaborating on GitHub

* To begin clone the repository
<br>
`git clone https://github.com/211025-Enterprise/Vanquish_GroceryApp.git`
<br><br>
* Next check out (co) from main, and make a new branch (-b).
<br>
`git co -b <New-Feature>`
<br><br>
* Your current branch can be verified with `git branch`
<br><br>
* Definitely commit after it compiles with no errors after you finish with something
<br><br>
* Whenever you are ready for your branch to be merged with main use `git push` and send a message in the discord letting everyone know your feature is complete
<br><br>
* From here the Merge Master will open the compare & pull request and make sure the merge is safe
<br><br>
Fatal Upstream head:
`git branch --set-upstream-to origin`
<br>
`git config push.default current`

FAQ help:
####How can I update the main branch with my branch’s changes?
* Switch to the main branch using the git checkout command, then merge the branch using the git merge command along with the branch name.

Resources I used:
<br>
[Branching Explained](https://www.varonis.com/blog/git-branching/)
<br>
[GitHub Collaborating guide](https://medium.com/@jonathanmines/the-ultimate-github-collaboration-guide-df816e98fb67)
