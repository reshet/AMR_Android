package net.sf.andpdf.pdfviewer;

import net.sf.andpdf.nio.ByteBuffer;

public class NetByteBuffer implements java.io.Serializable {

ByteBuffer buffer;

public NetByteBuffer(ByteBuffer buffer) {
    this.buffer = buffer;
}

public ByteBuffer getByteBuffer() {
    return this.buffer;
}
}
