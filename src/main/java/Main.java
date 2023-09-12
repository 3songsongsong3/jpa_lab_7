import entity.Member;
import entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_lab_7");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        // 프록시를 사용하지 않았을 때,
        // 아래 메서드에서 memberId로 회원 엔티티를 찾아서 회원은 물론, 회원과 연관된 팀의 이름도 출력한다.
        printUserAndTeam(em, "member1");

        // 프록시를 사용하지 않았을 때,
        // 회원 엔티티만 출력하는 데 사용하고, 회원과 연관된 팀 엔티티를 사용하지 않는다.
        // 회원 이름만 사용하는데, 데이터베이스에서는 팀 엔티티까지 조회 해둔다. (비효율적)
        printUser(em, "member1");

        // 위의 문제를 해결하기 위해, 데이터베이스 조회를 지연하는 방법을 제공한다. (지연 로딩)

        // 지연 로딩 기능을 사용하려면 실제 엔티티 객체 대신에, 데이터베이스 조회를 지연할 수 있는 가짜 객체가 필요하다.
        // ~~~~ 이를 프록시 객체라고 한다.
    }

    /**
     *
     * @param em
     * @param memberId
     * @date 2023-09-12
     */
    public static void printUserAndTeam(EntityManager em, String memberId) {
        Member member = em.find(Member.class, memberId);
        Team team = member.getTeam();
        System.out.println("회원 이름 : " + member.getUsername());
        System.out.println("팀 이름 : " + team.getName());
    }

    /**
     * 프록시를 사용하지 않았을 때, find()는 엔티티의 연관 엔티티까지 데이터베이스에서 조회한다.
     * @param em
     * @param memberId
     * @date 2023-09-12
     */
    public static void printUser(EntityManager em, String memberId) {
        Member member = em.find(Member.class, memberId);
        System.out.println("회원 이름 : " + member.getUsername());
    }

    /**
     * 프록시 객체 - 지연 로딩 메서드
     * @param em
     * @param memberId
     * @date 2023-09-12
     */
    public static void printUserByProxy(EntityManager em, String memberId) {
        // 하기 메서드 호출 시 JPA는 데이터베이스를 조회하지 않고, 실제 엔티티 객체도 생성하지 않는다.
        // 데이터베이스 접근을 위임한 프록시 객체를 반환한다. -> 실제 객체에 대한 참조를 보관한다.
        Member member = em.getReference(Member.class, memberId);
        // 프록시 객체의 메소드를 호출하면 프록시 객체는 실제 객체의 메소드를 호출한다.
        // member.getName() 처럼 실제 사용될 때 데이터베이스를 조회해서 실제 엔티티 객체를 생성한다. (프록시 객체의 초기화)
        System.out.println("회원 이름 : " + member.getUsername());
    }
}
