**Set Up**

Setting up first Jenkins pipeline using official Jenkins docker image
(Running a docker virtual machine in window 10 https://golb.hplar.ch/2019/01/docker-on-windows10-home-scratch.html)

Login to docker cli
```
  docker-machine ssh
```
 
Run first Jenkins image
```
  docker run -it -p 49001:8080 --privileged -v /var/run/docker.sock:/var/run/docker.sock  -v /home/docker/jenkins:/var/jenkins_home -t jenkins/jenkins
```
This should give you the running logs of the Jenkins server and you will also get the inital administrator password needed to setup Jenkins
 
Access the ui use the docker VM `<ip address>:49001` eg. `10.10.0.10:49001`

Follow set up on screen the Jenkins will restart, and you will be able to log in with the credentials you specified:
  
**Add Docker Plugin**

Manage Jenkins > Manage Plugins > Available
Filter on Docker > Check the box > Click install 
Check the restart when installation is complete box

_Once Jenkins restarts setup is completed_ 

**Create a pipeline** 

Click new item > Enter name > select ‘pipeline’ > click ok
This will take you to the Configure pipeline page

In the General tab select GitHub project and give github url to the project  
Set up a schedule for polling the SCM (* * * * *)
Add git credentials and Jenkinsfile path
save git credentials in Jenkins so they can be used across pipelines 
Click Apply >Click  Save

**Setting root password**
```Log into the Jenkins cmd line and set the root password
      docker@default:~$ docker exec -it -u root youthful_villani /bin/bash
      root@fd7e50cbdf4a:/# passwd -dl root
      passwd: password expiry information changed.
      root@fd7e50cbdf4a:/# passwd root
      Enter new UNIX password:
      Retype new UNIX password:
      passwd: password updated successfully
      root@fd7e50cbdf4a:/# exit
      exit
      docker@default:~$ docker exec --privileged -it youthful_villani /bin/bash
      jenkins@fd7e50cbdf4a:/$ su
      Password:
      root@fd7e50cbdf4a:/#
```

**Docker inside a Docker**

Inside the Jenkins container 
```apt-get update
   apt-get install vim
   cat /etc/apt/sources.list
   
   # deb http://snapshot.debian.org/archive/debian/20191224T000000Z stretch main
   deb http://deb.debian.org/debian stretch main
   # deb http://snapshot.debian.org/archive/debian-security/20191224T000000Z stretch/updates main
   deb http://security.debian.org/debian-security stretch/updates main
   # deb http://snapshot.debian.org/archive/debian/20191224T000000Z stretch-updates main
   deb http://deb.debian.org/debian stretch-updates main
   
   vi /etc/apt/sources.list
   
   deb http://ftp.ie.debian.org/debian/ stretch main contrib non-free
   deb-src http://ftp.ie.debian.org/debian/ stretch main contrib non-free
   deb [arch=amd64] https://download.docker.com/linux/debian stretch stable
   # deb-src [arch=amd64] https://download.docker.com/linux/debian stretch stable 
       
   apt-get update
   apt-get remove docker docker-engine docker.io containerd runc
   apt-get update
   apt-get install apt-transport-https ca-certificates curl gnupg2 software-properties-common
   curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -
   apt-key fingerprint 0EBFCD88
   add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable"
   apt-get update
   apt-get install docker-ce docker-ce-cli containerd.io
   
   usermod -aG docker jenkins
   docker ps
   Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running?
   service docker stop
   service docker start
   
   docker ps
   CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
   root@fd7e50cbdf4a:/#
   
   docker pull blazemeter/taurus
```


