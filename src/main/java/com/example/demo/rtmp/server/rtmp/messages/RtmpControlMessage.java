package com.example.demo.rtmp.server.rtmp.messages;

public abstract class RtmpControlMessage extends RtmpMessage {

	@Override
	public int getOutboundCsid() {
		return 2;
	}
}
