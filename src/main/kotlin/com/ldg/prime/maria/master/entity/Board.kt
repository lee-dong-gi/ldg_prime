package com.ldg.prime.maria.master.entity

import com.ldg.prime.maria.dto.request.board.BoardUpdateDto
import jakarta.persistence.*

@Entity
class Board(
        title: String,
        content: String,
        member: Member,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var title: String = title
        protected set

    @Column(nullable = false)
    var content: String = content
        protected set

    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL])
    var boardImages: MutableList<BoardImage> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    var member: Member = member
        protected set

    fun addBoardImage(boardImage: BoardImage) {
        this.boardImages.add(boardImage)
        boardImage.board = this
    }

    fun updateBoard(updateDto: BoardUpdateDto) {
        this.title = updateDto.title
        this.content = updateDto.content
    }

    fun modifyMember(member: Member) {
        this.member = member
    }
}