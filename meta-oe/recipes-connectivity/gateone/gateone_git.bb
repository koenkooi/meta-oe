DESCRIPTION = "HTML5 (plugin-free) web-based terminal emulator and SSH client"
LICENSE = "AGPLv3"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=ee5b168fc7de89a0cadc49e27830aa2c"

PR = "r1"

PV = "1.1"
SRCREV = "bb74e1095adb66b04b51ed6ff10ae0aa96afdd46"
SRC_URI = "git://github.com/liftoff/GateOne.git \
           file://gateone-avahi.service \
           file://gateone.service \
           file://logo.png \
           file://server.conf \
          "

S = "${WORKDIR}/git"

inherit distutils allarch systemd

export prefix = "${localstatedir}/lib"

do_install_append() {
	install -m 0755 -d ${D}${sysconfdir}/avahi/services/
	install -m 0644 ${WORKDIR}/gateone-avahi.service ${D}${sysconfdir}/avahi/services/
	install -m 0755 -d ${D}/var/lib/gateone/
	install -m 0755 -d ${D}/var/lib/gateone/plugins/
	install -m 0755 -d ${D}/var/lib/gateone/plugins/ssh
	install -m 0755 -d ${D}/var/lib/gateone/plugins/ssh/scripts/
	install -m 0644 ${WORKDIR}/logo.png ${D}/var/lib/gateone/plugins/ssh/scripts/logo.png
	install -m 0644 ${WORKDIR}/server.conf ${D}/var/lib/gateone/server.conf
}

NATIVE_SYSTEMD_SUPPORT = "1"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE = "gateone.service"

FILES_${PN} = "${localstatedir}/lib ${localstatedir}/log ${base_libdir} ${sysconfdir}"
RDEPENDS_${PN} = "python-unixadmin \
                  python-json \
                  python-logging \
                  python-fcntl \
                  python-tornado \
                  python-re \
                  python-readline \
                  python-datetime \
                  python-shell \
                  python-subprocess \
                  python-terminal \
                  python-io \
                  python-compression \
                  python-syslog \
                  python-misc \
                  python-crypt \
                  python-netclient \
                  python-email \
                  python-html \
                  python-textutils \
                  python-pyopenssl \
                  python-simplejson \
                  python-multiprocessing \
                  python-pkgutil \
                  python-imaging \
                  python-xml \
                  file \
                  openssh-ssh \
                  mime-support \
                 "
