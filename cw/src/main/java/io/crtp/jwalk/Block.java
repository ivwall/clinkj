package io.crtp.jwalk;
//https://github.com/johannbarbie/BitcoindClient4J/blob/master/src/main/java/com/_37coins/bcJsonRpc/pojo/Block.java

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown=true)
public class Block {

	private List<String> tx;
	private long time;
	private long height;
	private long nonce;
	private String hash;
	private String bits;
	private long difficulty;
	private String merkleroot;
	private String previousblockhash;
	private String nextblockhash;
	private long confirmations;
	private long version;
	private long size;

	private String tempASM = "not set";

	public void setTempASM(String t){
		tempASM = t;
		System.out.println(" >>>> block.setTempASM "+ tempASM);                 
	}

	public String getTempASM(){
		return tempASM;
	}
	
	public List<String> getTx() {
		return tx;
	}
	public void setTx(List<String> tx) {
		this.tx = tx;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getHeight() {
		return height;
	}
	public void setHeight(long height) {
		this.height = height;
	}
	public long getNonce() {
		return nonce;
	}
	public void setNonce(long nonce) {
		this.nonce = nonce;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getBits() {
		return bits;
	}
	public void setBits(String bits) {
		this.bits = bits;
	}
	public long getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(long difficulty) {
		this.difficulty = difficulty;
	}
	public String getMerkleroot() {
		return merkleroot;
	}
	public void setMerkleroot(String merkleroot) {
		this.merkleroot = merkleroot;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getConfirmations() {
		return confirmations;
	}
	public void setConfirmations(long confirmations) {
		this.confirmations = confirmations;
	}
	public String getPreviousblockhash() {
		return previousblockhash;
	}
	public void setPreviousblockhash(String previousblockhash) {
		this.previousblockhash = previousblockhash;
	}
	public String getNextblockhash() {
		return nextblockhash;
	}
	public void setNextblockhash(String nextblockhash) {
		this.nextblockhash = nextblockhash;
	}
	
}