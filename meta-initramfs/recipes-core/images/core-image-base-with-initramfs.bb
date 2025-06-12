require recipes-core/images/core-image-base.bb

# This image is meant to have an initramfs that can be (re)generated at runtime on the target itself

# For the initramfs generated at buildtime
DEPENDS += "dracut-native"

# For (re)generating the initramfs at runtime
CORE_IMAGE_EXTRA_INSTALL += " \
                             dracut \
                             btrfs-tools \
                            "


fakeroot dracut_initramfs () {
    set -x

    mkdir -p ${S}/dracut-tmp

    # This needs to use something like kernel-arch.bbclass, with a LUT to turn in into 'uname -m' output, e.g. armv7a -> armv7l
    export DRACUT_ARCH="aarch64"

    export DRACUT_TESTBIN="$(readlink ${IMAGE_ROOTFS}/bin/sh)"
    export DRACUT_INSTALL="${STAGING_LIBDIR_NATIVE}/dracut/dracut-install -v --debug"
    export DRACUT_INSTALL_PATH="/usr/bin:/usr/sbin"
    export DRACUT_KERNEL_VERSION="$(ls -1 ${IMAGE_ROOTFS}/lib/modules | tail -n1)"
    export SYSTEMCTL="$(which systemctl)"

    dracut --sysroot "${IMAGE_ROOTFS}" -v --force --tmpdir ${S}/dracut-tmp --kver $DRACUT_KERNEL_VERSION ${IMAGE_ROOTFS}/boot/initramfs-dracut.img 
}

ROOTFS_POSTPROCESS_COMMAND += "dracut_initramfs;"
