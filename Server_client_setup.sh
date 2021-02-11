#! /bin/sh
gnome-terminal -e 'sh -c "cd server; java server 9876"' --geometry 100x200+0+0
gnome-terminal -e 'sh -c "cd client; java client localhost 9876"' --geometry 100x10-100+0
gnome-terminal -e 'sh -c "cd client; java client localhost 9876"' --geometry 100x10-100+250
gnome-terminal -e 'sh -c "cd client; java client localhost 9876"' --geometry 100x10-100+500
gnome-terminal -e 'sh -c "cd client; java client localhost 9876"' --geometry 100x10-100+750
