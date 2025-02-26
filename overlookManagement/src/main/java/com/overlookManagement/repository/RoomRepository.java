package com.overlookManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import com.overlookManagement.entity.Room;
import com.overlookManagement.entity.User;

public interface RoomRepository extends JpaRepository<Room, Long> {

    
    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();

    //  query JPQL (Java Persistence Query Language)
    //  selezione di tutte le istanze dell'entità Room. La variabile r rappresenta ciascun oggetto Room della tabella.
    // LIKE, simbolo % è un carattere jolly che corrisponde a zero o più caratteri
    // filtro i risultati per includere solo le stanze il cui roomType contiene il valore della variabile roomType
    // AND r.id NOT IN (...): filtro esclude le stanze che sono già prenotate in un dato intervallo di date. 
    // precedere parametri con "":" indica un valore che deve essere fornito/sostituito al momento dell'esecuzione della query.
    @Query("SELECT r FROM Room r " +
    " WHERE r.roomType LIKE %:roomType% " +
    " AND r.id NOT IN " +
    " (SELECT bk.id FROM Booking bk " +
    " WHERE (bk.checkInDate <= :checkOutDate) " +
    " AND (bk.checkOutDate >= :checkInDate)) ")

    //chekc the 3 field in the checkox to select
    List<Room> findAvailableRoomsByDatesAndTypes(LocalDate checkInDate, LocalDate checkOutDate, String room);

    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT b.room.id FROM Booking b)")
    List<Room> getAllAvailableRooms();

    // non ci va findByEmail


}
