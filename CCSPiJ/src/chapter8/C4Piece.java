// C4Piece.java
// From Classic Computer Science Problems in Java Chapter 8
// Copyright 2020 David Kopec
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package chapter8;

public enum C4Piece implements Piece {
	B("B"),
	R("R"),
	E(" "); // E is Empty

	private String code;
	private C4Piece opposite;

	static {
		B.opposite = R;
		R.opposite = B;
		E.opposite = E;
	}

	private C4Piece(String c) {
		code = c;
	}

	@Override
	public C4Piece opposite() {
		return opposite;
	}

	@Override
	public String toString() {
		return code;
	}

}
