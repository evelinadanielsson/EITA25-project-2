#! /bin/sh
gnome-terminal -e 'sh -c "cd server; java server 9876"' --geometry 100x200+0+0
gnome-terminal -e 'sh -c "cd johannes_client;    java client localhost 9876"' --geometry 100x10-100+0
gnome-terminal -e 'sh -c "cd frans_client;   java client localhost 9876"' --geometry 100x10-100+250
gnome-terminal -e 'sh -c "cd evelina_client; java client localhost 9876"' --geometry 100x10-100+500
gnome-terminal -e 'sh -c "cd joel_client;    java client localhost 9876"' --geometry 100x10-100+750

# gnome-terminal -e 'sh -c "cd gov_client;    java client localhost 9876"' --geometry 100x10-100+750
# gnome-terminal -e 'sh -c "cd olivia_client;    java client localhost 9876"' --geometry 100x10-100+750
# gnome-terminal -e 'sh -c "cd oscar_client;    java client localhost 9876"' --geometry 100x10-100+750
# gnome-terminal -e 'sh -c "cd emma_client;    java client localhost 9876"' --geometry 100x10-100+750
