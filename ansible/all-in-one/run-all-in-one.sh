#!/usr/bin/env bash

ansible-galaxy collection install openstack.cloud:2.1.0
. ./openrc.sh; ansible-playbook -vv -i hosts all-in-one.yaml | tee output.txt
