/*
 * Decompiled with CFR 0.152.
 */
package zelix.utils.system;

import java.util.regex.Pattern;

public final class Version
implements Comparable<Version> {
    private static final Pattern SYNTAX = Pattern.compile("^[0-9]+\\.[0-9]+(?:\\.[0-9]+)?(?:pre[0-9]+)?$");
    private final int major;
    private final int minor;
    private final int patch;
    private final int preRelease;

    public Version(String version) {
        String[] parts;
        if (!SYNTAX.asPredicate().test(version)) {
            this.major = -1;
            this.minor = -1;
            this.patch = -1;
            this.preRelease = Integer.MAX_VALUE;
            return;
        }
        int indexOfPre = version.indexOf("pre");
        if (indexOfPre == -1) {
            this.preRelease = Integer.MAX_VALUE;
            parts = version.split("\\.");
        } else {
            this.preRelease = Integer.parseInt(version.substring(indexOfPre + 3));
            parts = version.substring(0, indexOfPre).split("\\.");
        }
        this.major = Integer.parseInt(parts[0]);
        this.minor = Integer.parseInt(parts[1]);
        this.patch = parts.length == 3 ? Integer.parseInt(parts[2]) : 0;
    }

    public int hashCode() {
        return this.major << 24 | this.minor << 16 | this.patch << 8 | this.preRelease;
    }

    public boolean equals(Object obj) {
        return super.equals(obj) || obj instanceof Version && this.compareTo((Version)obj) == 0;
    }

    @Override
    public int compareTo(Version o) {
        if (this.major != o.major) {
            return Integer.compare(this.major, o.major);
        }
        if (this.minor != o.minor) {
            return Integer.compare(this.minor, o.minor);
        }
        if (this.patch != o.patch) {
            return Integer.compare(this.patch, o.patch);
        }
        if (this.preRelease != o.preRelease) {
            return Integer.compare(this.preRelease, o.preRelease);
        }
        return 0;
    }

    public boolean isLowerThan(Version other) {
        return this.compareTo(other) < 0;
    }

    public boolean isLowerThan(String other) {
        return this.isLowerThan(new Version(other));
    }

    public boolean isHigherThan(Version other) {
        return this.compareTo(other) > 0;
    }

    public boolean isHigherThan(String other) {
        return this.isHigherThan(new Version(other));
    }

    public String toString() {
        if (this.isInvalid()) {
            return "(invalid version)";
        }
        String s = this.major + "." + this.minor;
        if (this.patch > 0) {
            s = s + "." + this.patch;
        }
        if (this.isPreRelease()) {
            s = s + "pre" + this.preRelease;
        }
        return s;
    }

    public boolean isInvalid() {
        return this.major == -1 && this.minor == -1 && this.patch == -1;
    }

    public boolean isPreRelease() {
        return this.preRelease != Integer.MAX_VALUE;
    }
}

