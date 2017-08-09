create or replace package pkg_jk_client AS

  --��������־
  procedure ADD_LOG(
      in_jk_type in varchar2,
      in_jk_sender in varchar2,
      in_jk_receiver in varchar2,
      in_jk_key in varchar2,
      in_jk_send_xml in varchar2,
      in_jk_id out varchar2
  );


  --��������־
  procedure UPDATE_LOG(
        log_id in varchar2,--��ˮ��
        in_jk_result in varchar2,--�ɹ���־ (0 ʧ�� 1 �ɹ�)
        in_jk_return_xml IN VARCHAR2 ,--����xml�� head
        in_jk_return_code IN VARCHAR2,--ҵ�������
        in_jk_return_msg in VARCHAR2 --ҵ�����˵��
  );

    --���jkkey
  procedure GET_KEY(
      in_ym in VARCHAR2,
      out_key OUT VARCHAR2
  );

end;
/
create or replace package body pkg_jk_client AS
    in_jk_id varchar2(10);

    /**-------------��������־---------------------------*/
    procedure ADD_LOG(
      in_jk_type in varchar2,
      in_jk_sender in varchar2,
      in_jk_receiver in varchar2,
      in_jk_key in varchar2,
      in_jk_send_xml in varchar2,
      in_jk_id out varchar2
    )
    as
    begin
       --������־
        select seq_log.nextval into in_jk_id from dual;
        insert into jk_client_log(
            jk_id, --�ӿ���־������
            jk_key, --ҵ����ˮ��
            jk_send_xml,--�ӿ���������xml head
            jk_type, --�ӿ�����
            jk_sender,--�ӿڵ��÷�
            jk_receiver,--�ӿڵ��÷�
            jk_call_begin--�ӿڵ��ÿ�ʼʱ��
        )
        values(
            in_jk_id, --�ӿ���־������
            in_jk_key, --ҵ����ˮ��
            in_jk_send_xml,--�ӿ���������xml head
            in_jk_type, --�ӿ�����
            in_jk_sender,--�ӿڵ��÷�
            in_jk_receiver,--�ӿڵ��÷�
            SYSDATE--�ӿڵ��ÿ�ʼʱ��
           );
    end;

    --��������־
    procedure UPDATE_LOG(
        log_id in varchar2,--��ˮ��
        in_jk_result in varchar2,--�ɹ���־ (0 ʧ�� 1 �ɹ�)
        in_jk_return_xml IN VARCHAR2 ,--����xml�� head
        in_jk_return_code IN VARCHAR2,--ҵ�������
        in_jk_return_msg in VARCHAR2 --ҵ�����˵��
    )
    as
    begin
       --��������־
       update jk_client_log t set
       t.jk_call_end=sysdate,
       jk_result=in_jk_result ,
       jk_return_xml=in_jk_return_xml,
       jk_return_code=in_jk_return_code,
       jk_return_msg=in_jk_return_msg
       where jk_id=log_id;
    end;



    --���jkkey
    procedure GET_KEY(
      in_ym in VARCHAR2,
      out_key OUT VARCHAR2
    ) AS
    ls_count INTEGER;
    BEGIN
       select count(*) INTO ls_count FROM jk_key t WHERE t.ym=in_ym;
       IF ls_count=0 THEN
         INSERT INTO jk_key(ym,SQNUM) VALUES (in_ym,0);
       END IF;
         UPDATE jk_key t SET SQNUM=SQNUM+1 WHERE  t.ym=in_ym;
         SELECT lpad(SQNUM,8,0) INTO out_key FROM jk_key t WHERE t.ym=in_ym;
    END;




end;
/
